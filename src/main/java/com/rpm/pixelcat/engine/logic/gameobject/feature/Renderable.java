package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;
import com.rpm.pixelcat.engine.logic.resource.Resource;

import java.awt.*;

public interface Renderable extends Feature {
    Resource getRenderableResource(GameObject gameObject) throws GameException;

    void setPosition(Point position);

    Point getPosition();

    void setLayer(Integer layer);

    Integer getLayer();

    static Renderable create(Point position, Integer layer) throws GameException {
        Renderable renderable = new RenderableImpl(position, layer);

        return renderable;
    }
}
