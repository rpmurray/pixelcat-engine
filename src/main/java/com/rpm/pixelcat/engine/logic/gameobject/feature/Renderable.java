package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;
import com.rpm.pixelcat.engine.logic.resource.Resource;

import java.awt.*;

public interface Renderable extends Feature {
    public Resource getRenderableResource(GameObject gameObject) throws GameException;

    public void setPosition(Point position);

    public Point getPosition();

    public void setLayer(Integer layer);

    public Integer getLayer();

    public static Renderable create() throws GameException {
        return Feature.create(Renderable.class);
    }
}
