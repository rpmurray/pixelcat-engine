package info.masterfrog.pixelcat.engine.logic.gameobject.feature;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.GameObject;
import info.masterfrog.pixelcat.engine.logic.resource.Resource;

import java.awt.*;

public interface Renderable extends Feature {
    Resource getRenderableResource(GameObject gameObject) throws TransientGameException;

    void setPosition(Point position);

    Point getPosition();

    void setLayer(Integer layer);

    Integer getLayer();

    void setScaleFactor(Double scaleFactor);

    Double getScaleFactor();

    static Renderable create(Point position, Integer layer) throws TransientGameException {
        Renderable renderable = new RenderableImpl(position, layer);

        return renderable;
    }

    static Renderable create(Point position, Integer layer, Double scaleFactor) throws TransientGameException {
        Renderable renderable = new RenderableImpl(position, layer, scaleFactor);

        return renderable;
    }
}
