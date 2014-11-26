package com.rpm.pixelcat.kernel;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.common.collect.ImmutableMap;
import com.rpm.pixelcat.common.Printer;
import com.rpm.pixelcat.exception.GameErrorCode;
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

public class Kernel extends JPanel implements KeyListener {
    // components
    private JFrame frame; // jframe to put the graphics into
    private Rectangle screen; // screen area
    private Renderer renderer; // renderer
    private KernelState kernelState; // game kernelState
    private LogicHandler logic; // game logic
    private HIDEventManager hidEventManager; // human interface device manager

    // utilities
    private static final Printer PRINTER = new Printer();

    // constants
    private static final Integer FPS = 60;
    private static final Integer LOOP_TIME = 1000/ FPS;

    public Kernel() {
        super();
    }

    public static void main(String arg[]) {
        try {
            // instantiate kernel
            Kernel kernel = new Kernel();

            // initialize
            kernel.init();

            while(true) {
                // record clock time
                Long loopStartClockTime = System.currentTimeMillis();

                // run game logic every frame
                kernel.run();

                // sleep for remainder of frame loop time
                kernel.sleep(loopStartClockTime);
            }
        } catch (ExitException e) {
            // game quit, not an issue
        } catch (Exception e) {
            PRINTER.printError(e);
        }

        // exit
        System.exit(0);
    }

    private void init() throws IOException, URISyntaxException {
        initGuice();
        initScreen();
        initRenderer();
        initKernelState();
        initHIDEventManager();
        initLogicHandler();
        initFrame();
    }

    private void initGuice() {
        Injector injector = Guice.createInjector(new GuiceModule());
        injector.injectMembers(this);
    }

    private void initRenderer() {
        renderer = new Renderer();
    }

    private void initKernelState() {
        kernelState = new KernelState(screen);
        kernelState.init();
    }

    private void initLogicHandler() throws IOException, URISyntaxException {
        logic = new LogicHandler(kernelState);
        addKeyListener(this);
    }

    private void initScreen() {
        // set up screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screen = new Rectangle(0, 0, (int) screenSize.getWidth() - 100, (int) screenSize.getHeight() - 100);
        this.setFocusable(true);
    }

    private void initFrame() {
        // set up frame
        frame = new JFrame("Video Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(50, 50);
        frame.setSize(screen.width, screen.height);
        frame.setContentPane(this);
        frame.setVisible(true);
    }

    private void initHIDEventManager() {
        hidEventManager = new HIDEventManager(kernelState);
    }

    public void run() throws GameException {
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
        Long currentClockTime = System.currentTimeMillis();
        Long loopTimeElapsed = currentClockTime - loopStartClockTime;
        Long remainingTime = LOOP_TIME - loopTimeElapsed;
        if (remainingTime < 0) {
            if (kernelState.getPropertyAsBoolean(KernelStatePropertyEnum.DEBUG_ENABLED)) {
                PRINTER.printError(new GameException(GameErrorCode.GAME_LOOP_TIME_EXCEEDED, ImmutableMap.of("remainingTime", remainingTime)));
            }
        } else if (remainingTime > 0) {
            Thread.sleep(remainingTime);
        }
    }

    private void handleErrors() {
        if (kernelState.getPropertyAsBoolean(KernelStatePropertyEnum.DEBUG_ENABLED)) {
            kernelState.getErrors().forEach(PRINTER::printError);
        }
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
