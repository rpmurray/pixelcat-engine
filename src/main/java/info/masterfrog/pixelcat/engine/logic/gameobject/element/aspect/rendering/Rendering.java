package info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.rendering;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.canvas.Canvas;
import info.masterfrog.pixelcat.engine.logic.gameobject.object.GameObject;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.Aspect;
import info.masterfrog.pixelcat.engine.logic.resource.Resource;

import java.awt.Point;

public interface Rendering extends Aspect {
    Resource getRenderableResource(GameObject gameObject) throws TransientGameException;

    Canvas getCanvas();

    void setCanvasNormalizedPosition(Point position);

    void setPosition(Point position);

    Point getCanvasNormalizedPosition();

    Point getPosition();

    void setLayer(Integer layer);

    Integer getLayer();

    void setScaleFactor(Double scaleFactor);

    Double getScaleFactor();

    static Rendering create(Canvas canvas, Point position, Integer layer) throws TransientGameException {
        Rendering rendering = new RenderingImpl(canvas, position, layer);

        return rendering;
    }

    static Rendering create(Canvas canvas, Point position, Integer layer, Double scaleFactor) throws TransientGameException {
        Rendering rendering = new RenderingImpl(canvas, position, layer, scaleFactor);

        return rendering;
    }
}
