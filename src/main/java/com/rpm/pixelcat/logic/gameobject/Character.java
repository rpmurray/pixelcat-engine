package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.common.Printer;
import com.rpm.pixelcat.logic.resource.model.SpriteResource;
import com.rpm.pixelcat.logic.resource.model.ResourceFactory;

import java.awt.*;

class Character extends GameObjectImpl implements GameObject {
    private SpriteResource resource;

    private static final Printer PRINTER = new Printer();

    Character(Rectangle bounds) {
        super(50, 50);
        resource = ResourceFactory.getInstance().createImageResource("nyancat.png", 0, 0, 250, 250);
        setCurrentResource(resource);
    }
}
