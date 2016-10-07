package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEventManager;
import info.masterfrog.pixelcat.engine.hid.HIDEventTypeEnum;
import info.masterfrog.pixelcat.engine.logic.LogicHandler;
import info.masterfrog.pixelcat.engine.renderer.RenderEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GraphicsPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private JFrame frame; // jframe to put the graphics into
    private KernelState kernelState;
    private RenderEngine renderEngine;
    private LogicHandler logicHandler;
    private HIDEventManager hidEventManager;

    GraphicsPanel(KernelState kernelState, RenderEngine renderEngine, LogicHandler logicHandler, HIDEventManager hidEventManager) {
        // handle parent
        super();

        // handle local init
        this.kernelState = kernelState;
        this.renderEngine = renderEngine;
        this.logicHandler = logicHandler;
        this.hidEventManager = hidEventManager;

        init();
    }

    private void init() {
        initScreen();
        initListeners();
        initFrame();
    }

    private void initScreen() {
        this.setFocusable(true);
    }

    private void initListeners() {
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    private void initFrame() {
        // set up frame
        frame = new JFrame("Video Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(100, 50);
        frame.setSize(
            ((Rectangle) kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS)).width,
            ((Rectangle) kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS)).height
        );
        frame.setVisible(true);
        frame.setContentPane(this);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void paintComponent(Graphics graphics) {
        // Get the drawing area bounds for game logic
        Rectangle screen = graphics.getClipBounds();

        // update logic handler with new display bounds
        try {
            kernelState.setProperty(KernelStatePropertyEnum.SCREEN_BOUNDS, screen);
        } catch (TransientGameException e) {
            kernelState.addTransientError(e);
        }

        // render frame
        try {
            renderEngine.process((Graphics2D) graphics, kernelState, logicHandler.getLayeredGameObjects(kernelState));
        } catch (TransientGameException e) {
            kernelState.addTransientError(e);
        }

        // notify parent thread
        //notifyAll();
    }

    public void keyPressed(KeyEvent event) {
        try {
            hidEventManager.handleKeyboardEvent(HIDEventTypeEnum.PRESS, event);
        } catch (TransientGameException e) {
            kernelState.addTransientError(e);
        }
    }

    public void keyReleased(KeyEvent event) {
        try {
            hidEventManager.handleKeyboardEvent(HIDEventTypeEnum.RELEASE, event);
        } catch (TransientGameException e) {
            kernelState.addTransientError(e);
        }
    }

    public void keyTyped(KeyEvent event) {
        // do nothing
    }

    public void mouseExited(MouseEvent event) {
        // do nothing
    }

    public void mouseEntered(MouseEvent event) {
        // do nothing
    }

    public void mouseMoved(MouseEvent event) {
        try {
            kernelState.setProperty(KernelStatePropertyEnum.MOUSE_POSITION, event.getLocationOnScreen());
        } catch (TransientGameException e) {
            kernelState.addTransientError(e);
        }
    }

    public void mousePressed(MouseEvent mouseEvent) {
        try {
            hidEventManager.handleMouseEvent(HIDEventTypeEnum.PRESS, mouseEvent);
        } catch (TransientGameException e) {
            kernelState.addTransientError(e);
        }
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        try {
            hidEventManager.handleMouseEvent(HIDEventTypeEnum.RELEASE, mouseEvent);
        } catch (TransientGameException e) {
            kernelState.addTransientError(e);
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        // do nothing
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        // do nothing
    }

    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        try {
            hidEventManager.handleMouseEvent(HIDEventTypeEnum.SCROLL, mouseWheelEvent);
        } catch (TransientGameException e) {
            kernelState.addTransientError(e);
        }
    }
}
