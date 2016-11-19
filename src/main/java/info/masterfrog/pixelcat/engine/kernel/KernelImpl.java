package info.masterfrog.pixelcat.engine.kernel;

//import com.google.inject.Guice;
//import com.google.inject.Injector;
import com.google.common.collect.ImmutableSet;
import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.exception.ExitException;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.RecoverableTerminalErrorException;
import info.masterfrog.pixelcat.engine.exception.TerminalErrorException;
import info.masterfrog.pixelcat.engine.exception.TerminalGameException;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEvent;
import info.masterfrog.pixelcat.engine.logic.LogicHandler;
import info.masterfrog.pixelcat.engine.hid.HIDEventManager;
import info.masterfrog.pixelcat.engine.logic.clock.AdvancedGameClock;
import info.masterfrog.pixelcat.engine.logic.clock.GameClock;
import info.masterfrog.pixelcat.engine.logic.gameobject.manager.GameObjectManager;
import info.masterfrog.pixelcat.engine.renderer.RenderEngine;
import info.masterfrog.pixelcat.engine.sound.SoundEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class KernelImpl implements Kernel {
    // components
    private RenderEngine renderEngine; // render engine
    private SoundEngine soundEngine; // sound engine
    private LogicHandler logicHandler; // game logic
    private HIDEventManager hidEventManager; // human interface device manager
    private GraphicsPanel graphicsPanel; // graphics panel

    // utilities
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(KernelImpl.class);

    public void init(HashMap<KernelStateProperty, Object> kernelStateInitProperties) throws TerminalErrorException {
        try {
            // object init
            initRenderEngine();
            initSoundEngine();
            initKernelState(kernelStateInitProperties);
            initHIDEventManager();
            initLogicHandler();
            initGraphicsPanel();

            // finalization
            finalizeSoundEngineInit();
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
                ((AdvancedGameClock) KernelState.getInstance().getMasterGameClockManager().getGameClock(KernelGameClock.LOOP_GAME_CLOCK)).addEvent("loop started");

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

            // shutdown necessary systems before exit
            shutdown();
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

    private void initRenderEngine() {
        renderEngine = new RenderEngine();
    }

    private void initSoundEngine() {
        new Thread(() -> {
            try {
                soundEngine = SoundEngine.getInstance().init();
            } catch (TerminalGameException e) {
                KernelState.getInstance().addTerminalError(e);
            }
        }).start();
    }

    private void finalizeSoundEngineInit() {
        // wait for sound engine initialization
        long t = 0L;
        while (!SoundEngine.getInstance().isInitialized()) {
            // wait for sound engine
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                PRINTER.printWarning(e);
            }

            // log
            t += 100L;
            PRINTER.printInfo("Slept for " + t + "ms total waiting for sound engine to initialize...");
        }
    }

    private void initKernelState(HashMap<KernelStateProperty, Object> kernelStateInitProperties) throws TerminalErrorException {
        KernelStateCore.getInstance().init(kernelStateInitProperties);
    }

    private void initLogicHandler() {
        logicHandler = LogicHandler.getInstance();
    }

    private void initHIDEventManager() {
        hidEventManager = HIDEventManager.create();
    }

    private void initGraphicsPanel() {
        graphicsPanel = new GraphicsPanel(renderEngine, logicHandler, hidEventManager);
    }

    private void shutdown() {
        shutdownSoundSystem();
    }

    private void shutdownSoundSystem() {
        SoundEngine.getInstance().shutdown();
    }

    public void registerGameObjectManagers(List<GameObjectManager> gameObjectManagers) throws RecoverableTerminalErrorException {
        try {
            for (GameObjectManager gameObjectManager : gameObjectManagers) {
                if (gameObjectManager == null) {
                    throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
                }
            }

            KernelState.getInstance().setProperty(KernelStateProperty.ACTIVE_GAME_OBJECT_MANAGERS, gameObjectManagers);
        } catch (TransientGameException e) {
            throw new RecoverableTerminalErrorException(ImmutableSet.of(e));
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
            KernelState.getInstance().addTransientError(e);
        } catch (TerminalGameException e) {
            KernelState.getInstance().addTerminalError(e);
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
            kernelInjectionMap.get(KernelInjectionEventEnum.PRE_PROCESSING).run();
        }
    }

    private void processLogic() throws TransientGameException, TerminalGameException, ExitException {
        // process logic
        logicHandler.process();
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
            AdvancedGameClock loopClock = (AdvancedGameClock) KernelState.getInstance().getMasterGameClockManager().getGameClock(KernelGameClock.LOOP_GAME_CLOCK);
            Long loopTimeElapsed = loopClock.getElapsed("loop started");
            loopClock.addEvent("loop logic ended");
            Integer loopDuration = KernelState.getInstance().getProperty(KernelStateProperty.GAME_LOOP_DURATION_NS);
            Long loopRemainingTime = loopDuration - loopTimeElapsed;
            if (loopRemainingTime < 0) {
                PRINTER.printWarning(
                    "The game loop exceeded the allotted time window for the current FPS configuration. [" +
                        String.format("%,d", loopRemainingTime) + "ns left of " +
                        String.format("%,d", loopDuration) + "ns]"
                );
            } else if (loopRemainingTime >= 0) {
                PRINTER.printDebug(
                    "The game loop was within the allotted time window for the current FPS configuration [" +
                        String.format("%,d", loopRemainingTime) + "ns left of " +
                        String.format("%,d", loopDuration) + "ns]"
                );

                if (loopRemainingTime > 0) {
                    Long sleepTime = GameClock.ns2ms(loopRemainingTime);
                    loopClock.addEvent("sleep started");
                    PRINTER.printDebug("Sleep " + sleepTime + "ms");
                    try {
                        Thread.sleep(sleepTime);
                    } catch (Exception e) {
                        throw new TransientGameException(GameEngineErrorCode.KERNEL_LOOP_SLEEP_FAILED, e);
                    }
                    loopClock.addEvent("sleep ended");
                }
            }
        } catch (TransientGameException e) {
            KernelState.getInstance().addTransientError(e);
        }

        // handle errors
        handleTransientErrors();
    }

    private void mapHIDEventsToKernelActions() {
        // fetch kernel action binder
        KernelActionBinder kernelActionBinder = KernelState.getInstance().getProperty(KernelStateProperty.KERNEL_ACTION_BINDER);

        // process hid events + generate kernel actions
        for (HIDEvent hidEvent : KernelState.getInstance().getHIDTriggeredEvents()) {
            try {
                // fetch binding
                KernelAction kernelAction = kernelActionBinder.resolveBinding(hidEvent);

                // set kernel action
                KernelState.getInstance().addKernelAction(kernelAction);
            } catch (TransientGameException e) {
                // do nothing, this just means there wasn't an action bound for this hid event
            }
        }
    }

    private void handleErrors() throws TerminalErrorException {
        // handle transient errors
        handleTransientErrors();

        // handle terminal errors
        if (KernelState.getInstance().hasTerminalErrors()) {
            throw new TerminalErrorException(KernelState.getInstance().getTerminalErrors());
        }
    }

    private void handleTransientErrors() {
        // log transient errors
        KernelState.getInstance().getTransientErrors().forEach(PRINTER::printError);

        // wipe out transient errors
        KernelState.getInstance().clearTransientErrors();
    }

    private void cleanUp() {
        // transition triggered hid events to sustained hid events
        KernelStateCore.getInstance().sustainHIDEvents();

        // reset certain temporary hid events
        KernelStateCore.getInstance().resetTransientHIDEvents();

        // reset certain temporary kernel actions
        KernelState.getInstance().resetTransientKernelActions();
    }
}
