package com.rpm.pixelcat.engine.kernel;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.logic.LogicHandler;
import com.rpm.pixelcat.engine.hid.HIDEventManager;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.exception.ExitException;
import com.rpm.pixelcat.engine.logic.LogicHandlerFactory;
import com.rpm.pixelcat.engine.logic.gameobject.GameObjectManager;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

class KernelImpl implements Kernel {
    // components
    private com.rpm.pixelcat.engine.renderer.Renderer renderer; // renderer
    private Rectangle screen; // screen area
    private KernelStateImpl kernelState; // game kernelState
    private LogicHandler logicHandler; // game logic
    private HIDEventManager hidEventManager; // human interface device manager
    private GraphicsPanel graphicsPanel; // graphics panel

    // utilities
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(KernelImpl.class);

    KernelImpl() {
        super();
    }

    public void init() throws IOException, URISyntaxException, GameException {
        initGuice();
        initRenderer();
        initScreen();
        initKernelState();
        initHIDEventManager();
        initLogicHandler();
        initGraphicsPanel();
    }

    public void kernelMain(Map<KernelInjectionEventEnum, KernelInjection> kernelInjectionMap) throws Exception {
        try {
            Integer loopCounter = 0;
            while(true) {
                // record clock time
                kernelState.getMasterGameClockManager().getGameClock(KernelState.LOOP_GAME_CLOCK).addEvent("loop started");

                // debug game loop
                PRINTER.printDebug("Game loop counter: " + loopCounter++);

                // run game logic every frame
                run(kernelInjectionMap);

                // sleep for remainder of frame loop time
                sleep();
            }
        } catch (ExitException e) {
            // game quit, not an issue
            PRINTER.printWarning("Game exit condition met.");
        } catch (Exception e) {
            // log error
            PRINTER.printError(e);

            // throw error to caller
            throw e;
        }
    }

    private void initGuice() {
        Injector injector = Guice.createInjector(new KernelGuiceModule());
        injector.injectMembers(this);
    }

    private void initRenderer() {
        renderer = new com.rpm.pixelcat.engine.renderer.Renderer();
    }

    private void initScreen() {
        // set up screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screen = new Rectangle(0, 0, (int) screenSize.getWidth() - 200, (int) screenSize.getHeight() - 400);
    }

    private void initKernelState() throws GameException {
        kernelState = new KernelStateImpl(screen);
        kernelState.init();
    }

    private void initLogicHandler() {
        logicHandler = LogicHandlerFactory.getInstance().createLogicHandler();
    }

    private void initHIDEventManager() {
        hidEventManager = HIDEventManager.create(kernelState);
    }

    private void initGraphicsPanel() {
        graphicsPanel = new GraphicsPanel(kernelState, renderer, logicHandler, hidEventManager);
    }

    public KernelState getKernelState() {
        return kernelState;
    }

    public void registerGameObjectManagers(List<GameObjectManager> gameObjectManagers) throws GameException {
        kernelState.setProperty(KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS, gameObjectManagers);
    }

    public void run(Map<KernelInjectionEventEnum, KernelInjection> kernelInjectionMap) throws GameException {
        // synthesize special HID events
        hidEventManager.generateSynthesizedEvents();

        // handle mapping hid events to kernel actions
        mapHIDEventsToKernelActions();

        // handle pre-processing kernel injection
        if (kernelInjectionMap.containsKey(KernelInjectionEventEnum.PRE_PROCESSING)) {
            kernelInjectionMap.get(KernelInjectionEventEnum.PRE_PROCESSING).run(kernelState);
        }

        // process logic
        logicHandler.process(kernelState);

        // handle errors
        handleErrors();

        // repaint panel
        graphicsPanel.getFrame().repaint();

        // clean up
        cleanUp();
    }

    public void sleep() throws Exception {
        Long loopTimeElapsed = kernelState.getMasterGameClockManager().getGameClock(KernelState.LOOP_GAME_CLOCK).getElapsed("loop started");
        kernelState.getMasterGameClockManager().getGameClock(KernelState.LOOP_GAME_CLOCK).addEvent("loop logic ended");
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
                kernelState.getMasterGameClockManager().getGameClock(KernelState.LOOP_GAME_CLOCK).addEvent("sleep started");
                PRINTER.printInfo("Sleep " + loopRemainingTime + "ms");
                Thread.sleep(loopRemainingTime/1000000);
                kernelState.getMasterGameClockManager().getGameClock(KernelState.LOOP_GAME_CLOCK).addEvent("sleep ended");
            }
        }
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
            } catch (GameException e) {
                // do nothing, this just means there wasn't an action bound for this hid event
            }
        }
    }

    private void handleErrors() {
            kernelState.getErrors().forEach(PRINTER::printError);
    }

    private void cleanUp() {
        // reset certain temporary hid events
        kernelState.resetTransientHIDEvents();

        // reset certain temporary kernel actions
        kernelState.resetTransientKernelActions();

        // wipe out errors
        kernelState.clearErrors();
    }
}
