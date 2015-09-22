package com.rpm.pixelcat.engine.kernel;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.LogicHandler;
import com.rpm.pixelcat.engine.renderer.Renderer;

import javax.swing.*;
import java.awt.*;

public class GraphicsPanel extends JPanel {
    private KernelState kernelState;
    private Renderer renderer;
    private LogicHandler logicHandler;

    public GraphicsPanel(KernelState kernelState, Renderer renderer, LogicHandler logicHandler) {
        // handle parent
        super();

        // handle local init
        this.kernelState = kernelState;
        this.renderer = renderer;
        this.logicHandler = logicHandler;
    }

    public void paintComponent(Graphics graphics) {
        // Get the drawing area bounds for game logic
        Rectangle screen = graphics.getClipBounds();

        // update logic handler with new display bounds
        kernelState.setBounds(screen);

        // render frame
        try {
            renderer.render((Graphics2D) graphics, kernelState, logicHandler.getLayeredGameObjects(kernelState));
        } catch (GameException e) {
            kernelState.addError(e);
        }

        // notify parent thread
        //notifyAll();
    }
}
