package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.logic.resource.model.ResourceFactory;
import com.rpm.pixelcat.logic.resource.model.TextResource;

import java.awt.*;

public class Subtitle extends GameObjectImpl implements GameObject {
    private TextResource resource;

    Subtitle(Rectangle bounds) {
        super(bounds.width / 2 - 200, bounds.height / 2 - 30);
        resource = ResourceFactory.getInstance().createTextResource(
            "The 2D Sprite Base Video Game Engine", new Font("Courier New", Font.BOLD, 20)
        );
        setCurrentResource(resource);
    }
}
