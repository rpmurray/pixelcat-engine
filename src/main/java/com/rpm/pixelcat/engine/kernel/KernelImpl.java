package com.rpm.pixelcat.engine.kernel;

//import com.google.inject.Guice;
//import com.google.inject.Injector;
import com.google.common.collect.ImmutableSet;
import com.rpm.pixelcat.engine.common.printer.Printer;
import com.rpm.pixelcat.engine.common.printer.PrinterFactory;
import com.rpm.pixelcat.engine.exception.*;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.logic.LogicHandler;
import com.rpm.pixelcat.engine.hid.HIDEventManager;
import com.rpm.pixelcat.engine.logic.LogicHandlerFactory;
import com.rpm.pixelcat.engine.logic.clock.AdvancedGameClock;
import com.rpm.pixelcat.engine.logic.clock.GameClock;
import com.rpm.pixelcat.engine.logic.gameobject.GameObjectManager;
import com.rpm.pixelcat.engine.renderer.RenderEngine;
import com.rpm.pixelcat.engine.sound.SoundEngine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class KernelImpl implements Kernel {
    // components
    private RenderEngine renderEngine; // render engine
    private SoundEngine soundEngine; // sound engine
    private KernelStateImpl kernelState; // game kernelState
    private LogicHandler logicHandler; // game logic
    private HIDEventManager hidEventManager; // human interface device manager
    private GraphicsPanel graphicsPanel; // graphics panel

    // utilities
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(KernelImpl.class);

    public void init(HashMap<KernelStatePropertyEnum, Object> kernelStateInitProperties) throws TerminalErrorException {
        try {
            //initGuice();
            initRenderer();
            initSoundPlayer();
            initKernelState(kernelStateInitProperties);
            initHIDEventManager();
            initLogicHandler();
            initGraphicsPanel();
        } catch (TerminalErrorException e) {
            PRINTER.printError(e);

            throw e;
        }
    }

    public void kernelMain(Map<KernelInjectionEventEnum, KernelInjection> kernelInjectionMap) {
        try {
            Integer loopCounter = 0;
            while (true) {
                // record clock time
                ((AdvancedGameClock) kernelState.getMasterGameClockManager().getGameClock(KernelState.LOOP_GAME_CLOCK)).addEvent("loop started");

                // debug game loop
                PRINTER.printDebug("Game loop counter: " + loopCounter++);

                // run game logic every frame
                run(kernelInjectionMap);

                // sleep for remainder of frame loop time
                sleep();
            }
        } catch (ExitException e) {
            // game quit, not an issue
            PRINTER.printWarning("Normal game exit condition met. Game will exit...");
        } catch (TerminalErrorException e) {
            // log terminal error
            PRINTER.printWarning("Terminal error(s) encountered! Game will forcibly exit!");
            PRINTER.printError(e);
        }
    }

    /*
    private void initGuice() {
        Injector injector = Guice.createInjector(new KernelGuiceModule());
        injector.injectMembers(this);
    }
    */

    private void initRenderer() {
        renderEngine = new RenderEngine();
    }

    private void initSoundPlayer() {
        new Thread(() -> {
            try {
                soundEngine = SoundEngine.getInstance().init();
            } catch (TerminalGameException e) {
                kernelState.addTerminalError(e);
            }
        });
    }

    private void initKernelState(HashMap<KernelStatePropertyEnum, Object> kernelStateInitProperties) throws TerminalErrorException {
        try {
            kernelState = new KernelStateImpl();
            kernelState.init(kernelStateInitProperties);
        } catch (TerminalGameException e) {
            throw new TerminalErrorException(ImmutableSet.of(e));
        }
    }

    private void initLogicHandler() {
        logicHandler = LogicHandlerFactory.getInstance().createLogicHandler();
    }

    private void initHIDEventManager() {
        hidEventManager = HIDEventManager.create(kernelState);
    }

    private void initGraphicsPanel() {
        graphicsPanel = new GraphicsPanel(kernelState, renderEngine, logicHandler, hidEventManager);
    }

    public KernelState getKernelState() {
        return kernelState;
    }

    public void registerGameObjectManagers(List<GameObjectManager> gameObjectManagers) {
        try {
            kernelState.setProperty(KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS, gameObjectManagers);
        } catch (TransientGameException e) {
            PRINTER.printWarning("Transient error: " + e);
        }
    }

    private void run(Map<KernelInjectionEventEnum, KernelInjection> kernelInjectionMap) throws TerminalErrorException, ExitException {
        try {
            // set up
            setUp(kernelInjectionMap);

            // process logic
            processLogic();

            // handle graphics engine updates
            processGraphics();

            // handle sound engine updates
            processSound();
        } catch (TransientGameException e) {
            kernelState.addTransientError(e);
        } catch (TerminalGameException e) {
            kernelState.addTerminalError(e);
        }

        // handle errors
        handleErrors();

        // clean up
        cleanUp();
    }

    private void setUp(Map<KernelInjectionEventEnum, KernelInjection> kernelInjectionMap) throws TransientGameException, TerminalGameException {
        // synthesize special HID events
        hidEventManager.generateSynthesizedEvents();

        // handle mapping hid events to kernel actions
        mapHIDEventsToKernelActions();

        // handle pre-processing kernel injection
        if (kernelInjectionMap.containsKey(KernelInjectionEventEnum.PRE_PROCESSING)) {
            kernelInjectionMap.get(KernelInjectionEventEnum.PRE_PROCESSING).run(kernelState);
        }
    }

    private void processLogic() throws TransientGameException, TerminalGameException, ExitException {
        // process logic
        logicHandler.process(kernelState);
    }

    private void processGraphics() throws TransientGameException, TerminalGameException {
        // process graphics rendering
        graphicsPanel.getFrame().repaint();
    }

    private void processSound() throws TransientGameException, TerminalGameException {
        // process sound generation
        soundEngine.process(logicHandler.getSoundEvents());
    }

    private void sleep() {
        try {
            AdvancedGameClock loopClock = (AdvancedGameClock) kernelState.getMasterGameClockManager().getGameClock(KernelState.LOOP_GAME_CLOCK);
            Long loopTimeElapsed = loopClock.getElapsed("loop started");
            loopClock.addEvent("loop logic ended");
            Integer loopDuration = (Integer) kernelState.getProperty(KernelStatePropertyEnum.GAME_LOOP_DURATION_NS);
            Long loopRemainingTime = loopDuration - loopTimeElapsed;
            if (loopRemainingTime < 0) {
                PRINTER.printWarning(
                    "The game loop exceeded the allotted time window for the current FPS configuration. [" +
                        String.format("%,d", loopRemainingTime) + "ns left of " +
                        String.format("%,d", loopDuration) + "ns]"
                );
            } else if (loopRemainingTime >= 0) {
                PRINTER.printInfo(
                    "The game loop was within the allotted time window for the current FPS configuration [" +
                        String.format("%,d", loopRemainingTime) + "ns left of " +
                        String.format("%,d", loopDuration) + "ns]"
                );

                if (loopRemainingTime > 0) {
                    Long sleepTime = GameClock.toMS(loopRemainingTime);
                    loopClock.addEvent("sleep started");
                    PRINTER.printInfo("Sleep " + sleepTime + "ms");
                    try {
                        Thread.sleep(sleepTime);
                    } catch (Exception e) {
                        throw new TransientGameException(GameErrorCode.KERNEL_LOOP_SLEEP_FAILED, e);
                    }
                    loopClock.addEvent("sleep ended");
                }
            }
        } catch (TransientGameException e) {
            kernelState.addTransientError(e);
        }

        // handle errors
        handleTransientErrors();
    }

    private void mapHIDEventsToKernelActions() {
        // fetch kernel action binder
        KernelActionBinder kernelActionBinder = (KernelActionBinder) kernelState.getProperty(KernelStatePropertyEnum.KERNEL_ACTION_BINDER);

        // process hid events + generate kernel actions
        for (HIDEventEnum hidEvent : kernelState.getHIDEvents()) {
            try {
                // fetch binding
                KernelActionEnum kernelAction = kernelActionBinder.binding(hidEvent);

                // set kernel action
                kernelState.addKernelAction(kernelAction);
            } catch (TransientGameException e) {
                // do nothing, this just means there wasn't an action bound for this hid event
            }
        }
    }

    private void handleErrors() throws TerminalErrorException {
        // handle transient errors
        handleTransientErrors();

        // handle terminal errors
        if (kernelState.hasTerminalErrors()) {
            throw new TerminalErrorException(kernelState.getTerminalErrors());
        }
    }

    private void handleTransientErrors() {
        // log transient errors
        kernelState.getTransientErrors().forEach(PRINTER::printError);

        // wipe out transient errors
        kernelState.clearTransientErrors();
    }

    private void cleanUp() {
        // reset certain temporary hid events
        kernelState.resetTransientHIDEvents();

        // reset certain temporary kernel actions
        kernelState.resetTransientKernelActions();
    }
}
