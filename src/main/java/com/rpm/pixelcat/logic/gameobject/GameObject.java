package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.logic.resource.model.Resource;

import java.awt.*;

public interface GameObject {
    public void setCurrentResource(Resource resource);

    public Resource getCurrentResource();

    public void setPosition(Integer x, Integer y);

    public void setPosition(Point position);

    public Point getPosition();

    public void setLayer(Integer layer);

    public Integer getLayer();
}
