package info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.canvas;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.kernel.KernelStateProperty;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.Aspect;

import java.awt.Point;
import java.awt.Rectangle;

public interface Canvas extends Aspect {
    Canvas setPosition(Point position);

    Point getPosition();

    Canvas setBounds(Rectangle bounds);

    Rectangle getBounds();

    Canvas setScaleFactor(Double scaleFactor);

    Double getScaleFactor();

    static Canvas create(Rectangle bounds) throws TransientGameException {
        Canvas canvas = new CanvasImpl(new Point(0, 0), bounds, 1.0);

        return canvas;
    }

    static Canvas createFromKernel() throws TransientGameException {
        Canvas canvas = new CanvasImpl(
            new Point(0, 0),
            KernelState.getInstance().getProperty(KernelStateProperty.SCREEN_BOUNDS),
            1.0
        );

        return canvas;
    }

    static Canvas create(Point position, Rectangle bounds) throws TransientGameException {
        Canvas canvas = new CanvasImpl(position, bounds, 1.0);

        return canvas;
    }

    static Canvas create(Point position, Rectangle bounds, Double scaleFactor) throws TransientGameException {
        Canvas canvas = new CanvasImpl(position, bounds, scaleFactor);

        return canvas;
    }
}
