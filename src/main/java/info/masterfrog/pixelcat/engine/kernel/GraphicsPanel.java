package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.hid.HIDEventManager;
import info.masterfrog.pixelcat.engine.hid.HIDEventTypeEnum;
import info.masterfrog.pixelcat.engine.logic.LogicHandler;
import info.masterfrog.pixelcat.engine.renderer.RenderEngine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

class GraphicsPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private JFrame frame; // jframe to put the graphics into
    private RenderEngine renderEngine;
    private LogicHandler logicHandler;
    private HIDEventManager hidEventManager;

    GraphicsPanel(RenderEngine renderEngine, LogicHandler logicHandler, HIDEventManager hidEventManager) {
        // handle parent
        super();

        // handle local init
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
        frame.setLocation(
            KernelState.getInstance().<Rectangle>getProperty(KernelStateProperty.SCREEN_BOUNDS).x,
            KernelState.getInstance().<Rectangle>getProperty(KernelStateProperty.SCREEN_BOUNDS).y
        );
        frame.setSize(
            KernelState.getInstance().<Rectangle>getProperty(KernelStateProperty.SCREEN_BOUNDS).width,
            KernelState.getInstance().<Rectangle>getProperty(KernelStateProperty.SCREEN_BOUNDS).height
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
            KernelState.getInstance().setProperty(KernelStateProperty.SCREEN_BOUNDS, screen);
        } catch (TransientGameException e) {
            KernelState.getInstance().addTransientError(e);
        }

        // render frame
        try {
            renderEngine.process((Graphics2D) graphics, logicHandler.getLayeredGameObjects());
        } catch (TransientGameException e) {
            KernelState.getInstance().addTransientError(e);
        }

        // notify parent thread
        //notifyAll();
    }

    public void keyPressed(KeyEvent event) {
        try {
            hidEventManager.handleKeyboardEvent(HIDEventTypeEnum.PRESS, event);
        } catch (TransientGameException e) {
            KernelState.getInstance().addTransientError(e);
        }
    }

    public void keyReleased(KeyEvent event) {
        try {
            hidEventManager.handleKeyboardEvent(HIDEventTypeEnum.RELEASE, event);
        } catch (TransientGameException e) {
            KernelState.getInstance().addTransientError(e);
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
            KernelState.getInstance().setProperty(KernelStateProperty.MOUSE_POSITION, event.getLocationOnScreen());
        } catch (TransientGameException e) {
            KernelState.getInstance().addTransientError(e);
        }
    }

    public void mousePressed(MouseEvent mouseEvent) {
        try {
            hidEventManager.handleMouseEvent(HIDEventTypeEnum.PRESS, mouseEvent);
        } catch (TransientGameException e) {
            KernelState.getInstance().addTransientError(e);
        }
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        try {
            hidEventManager.handleMouseEvent(HIDEventTypeEnum.RELEASE, mouseEvent);
        } catch (TransientGameException e) {
            KernelState.getInstance().addTransientError(e);
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
            KernelState.getInstance().addTransientError(e);
        }
    }
}
