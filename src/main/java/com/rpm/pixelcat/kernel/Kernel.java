package com.rpm.pixelcat.kernel;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.rpm.pixelcat.common.Printer;
import com.rpm.pixelcat.hid.HIDEventManager;
import com.rpm.pixelcat.hid.HIDKeyboardEventTypeEnum;
import com.rpm.pixelcat.logic.LogicHandler;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.exception.ExitException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class Kernel extends JPanel implements KeyListener {
    // components
    private JFrame frame; // jframe to put the graphics into
    private Rectangle screen; // screen area
    private Renderer renderer; // renderer
    private KernelState kernelState; // game kernelState
    private LogicHandler logic; // game logic
    private HIDEventManager hidEventManager; // human interface device manager

    // utilities
    private static final Printer PRINTER = new Printer(Kernel.class);

    // constants
    private static final Integer FPS = 60;
    private static final Integer LOOP_TIME = 1000/ FPS;

    public Kernel() {
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
                // debug game loop
                PRINTER.printDebug("Game loop counter: " + loopCounter++);

                // record clock time
                Long loopStartClockTime = System.currentTimeMillis();

                // run game logic every frame
                run(kernelInjectionMap);

                // sleep for remainder of frame loop time
                sleep(loopStartClockTime);
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
        Injector injector = Guice.createInjector(new GuiceModule());
        injector.injectMembers(this);
    }

    private void initRenderer() {
        renderer = new Renderer();
    }

    private void initKernelState() throws GameException {
        kernelState = new KernelState(screen, System.currentTimeMillis());
        kernelState.init();
    }

    private void initLogicHandler() throws IOException, URISyntaxException, GameException {
        logic = new LogicHandler(kernelState);
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

    public void run(Map<KernelInjectionEventEnum, KernelInjection> kernelInjectionMap) throws GameException {
        // synthesize special HID events
        hidEventManager.generateSynthesizedEvents();

        // handle pre-processing kernel injection
        if (kernelInjectionMap.containsKey(KernelInjectionEventEnum.PRE_PROCESSING)) {
            kernelInjectionMap.get(KernelInjectionEventEnum.PRE_PROCESSING).run(kernelState);
        }

        // process logic
        logic.process(kernelState);

        // handle errors
        handleErrors();

        // repaint panel
        frame.repaint();

        // clean up
        cleanUp();
    }

    public void sleep(Long loopStartClockTime) throws Exception {
        kernelState.setClockTime(System.currentTimeMillis());
        Long loopTimeElapsed = kernelState.getClockTime() - loopStartClockTime;
        Long loopRemainingTime = LOOP_TIME - loopTimeElapsed;
        if (loopRemainingTime < 0) {
                PRINTER.printWarning("The game loop exceeded the allotted time window for the current FPS configuration. [" + loopRemainingTime + "ms]");
        } else if (loopRemainingTime >= 0) {
                PRINTER.printInfo("The game loop was within the allotted time window for the current FPS configuration [" + loopRemainingTime + "ms]");

            if (loopRemainingTime > 0) {
                Thread.sleep(loopRemainingTime);
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
        renderer.render((Graphics2D) g, kernelState, logic.getLayeredGameObjects());
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
