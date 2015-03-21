package com.rpm.pixelcat.engine.kernel;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.logic.LogicHandler;
import com.rpm.pixelcat.engine.hid.HIDEventManager;
import com.rpm.pixelcat.engine.hid.HIDKeyboardEventTypeEnum;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.exception.ExitException;
import com.rpm.pixelcat.engine.logic.LogicHandlerFactory;
import com.rpm.pixelcat.engine.logic.gameobject.GameObjectManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

class KernelImpl extends JPanel implements Kernel, KeyListener {
    // components
    private JFrame frame; // jframe to put the graphics into
    private Rectangle screen; // screen area
    private com.rpm.pixelcat.engine.renderer.Renderer renderer; // renderer
    private KernelStateImpl kernelState; // game kernelState
    private LogicHandler logicHandler; // game logic
    private HIDEventManager hidEventManager; // human interface device manager

    // utilities
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(KernelImpl.class);

    KernelImpl() {
        super();
    }

    public void init() throws IOException, URISyntaxException, GameException {
        initGuice();
        initScreen();
        initRenderer();
        initKernelState();
        initHIDEventManager();
        initLogicHandler();
        initFrame();
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

    private void initKernelState() throws GameException {
        kernelState = new KernelStateImpl(screen);
        kernelState.init();
    }

    private void initLogicHandler() {
        logicHandler = LogicHandlerFactory.getInstance().createLogicHandler();
        addKeyListener(this);
    }

    private void initScreen() {
        // set up screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screen = new Rectangle(0, 0, (int) screenSize.getWidth() - 200, (int) screenSize.getHeight() - 400);
        this.setFocusable(true);
    }

    private void initFrame() {
        // set up frame
        frame = new JFrame("Video Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(100, 50);
        frame.setSize(screen.width, screen.height);
        frame.setContentPane(this);
        frame.setVisible(true);
    }

    private void initHIDEventManager() {
        hidEventManager = new HIDEventManager(kernelState);
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

        // handle pre-processing kernel injection
        if (kernelInjectionMap.containsKey(KernelInjectionEventEnum.PRE_PROCESSING)) {
            kernelInjectionMap.get(KernelInjectionEventEnum.PRE_PROCESSING).run(kernelState);
        }

        // process logic
        logicHandler.process(kernelState);

        // handle errors
        handleErrors();

        // repaint panel
        frame.repaint();

        // clean up
        cleanUp();
    }

    public void sleep() throws Exception {
        Long loopTimeElapsed = kernelState.getMasterGameClockManager().getGameClock(KernelState.LOOP_GAME_CLOCK).getElapsed("loop started");
        kernelState.getMasterGameClockManager().getGameClock(KernelState.LOOP_GAME_CLOCK).addEvent("loop logic ended");
        Long loopRemainingTime = (Integer) kernelState.getProperty(KernelStatePropertyEnum.GAME_LOOP_DURATION_MS) - loopTimeElapsed;
        if (loopRemainingTime < 0) {
            PRINTER.printWarning("The game loop exceeded the allotted time window for the current FPS configuration. [" + loopRemainingTime + "ms]");
        } else if (loopRemainingTime >= 0) {
            PRINTER.printInfo("The game loop was within the allotted time window for the current FPS configuration [" + loopRemainingTime + "ms]");

            if (loopRemainingTime > 0) {
                kernelState.getMasterGameClockManager().getGameClock(KernelState.LOOP_GAME_CLOCK).addEvent("sleep started");
                Thread.sleep(loopRemainingTime);
                kernelState.getMasterGameClockManager().getGameClock(KernelState.LOOP_GAME_CLOCK).addEvent("sleep ended");
            }
        }
    }

    private void handleErrors() {
            kernelState.getErrors().forEach(PRINTER::printError);
    }

    private void cleanUp() {
        // wipe out errors
        kernelState.clearErrors();
    }

    // Now the instance methods:
    public void paintComponent(Graphics g) {
        // Get the drawing area bounds for game logic
        screen = g.getClipBounds();

        // update logic handler with new display bounds
        kernelState.setBounds(screen);

        // render frame
        try {
            renderer.render((Graphics2D) g, kernelState, logicHandler.getLayeredGameObjects(kernelState));
        } catch (GameException e) {
            kernelState.addError(e);
        }
    }

    public void keyPressed(KeyEvent key) {
        try {
            hidEventManager.handleKeyboardEvent(HIDKeyboardEventTypeEnum.KEY_PRESS, key);
        } catch (GameException e) {
            kernelState.addError(e);
        }
    }

    public void keyReleased(KeyEvent key) {
        try {
            hidEventManager.handleKeyboardEvent(HIDKeyboardEventTypeEnum.KEY_RELEASE, key);
        } catch (GameException e) {
            kernelState.addError(e);
        }
    }

    public void keyTyped(KeyEvent key) {
    }
}
