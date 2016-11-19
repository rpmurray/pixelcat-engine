package info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.canvas;

import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.AspectImpl;

import java.awt.Point;
import java.awt.Rectangle;

class CanvasImpl extends AspectImpl implements Canvas {
    private Point position;
    private Rectangle bounds;
    private Double scaleFactor;

    CanvasImpl(Point position, Rectangle bounds, Double scaleFactor) {
        this.position = position;
        this.bounds = bounds;
        this.scaleFactor = scaleFactor;
    }

    public Canvas setPosition(Point position) {
        this.position = position;

        return this;
    }

    public Point getPosition() {
        return position;
    }

    public Canvas setBounds(Rectangle bounds) {
        this.bounds = bounds;

        return this;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Canvas setScaleFactor(Double scaleFactor) {
        this.scaleFactor = scaleFactor;

        return this;
    }

    public Double getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public String toString() {
        return "CanvasImpl{" +
            "position=" + position +
            ", bounds=" + bounds +
            ", scaleFactor=" + scaleFactor +
            '}';
    }
}
