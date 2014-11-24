package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.common.Printer;
import com.rpm.pixelcat.logic.resource.model.ImageResource;
import com.rpm.pixelcat.logic.resource.model.ResourceFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

class Title extends GameObjectImpl implements GameObject {
    private ImageResource resource;

    private static Rectangle dimensions = new Rectangle(0, 0, 200, 100);
    private static final Printer PRINTER = new Printer();

    Title(Rectangle bounds) {
        super((bounds.width - dimensions.width) / 2, (bounds.height - dimensions.height) / 2 - 100);
        resource = ResourceFactory.getInstance().createImageResource(
            "pixelcat.png",
            dimensions.x, dimensions.y, dimensions.x + dimensions.width, dimensions.y + dimensions.height
        );
        setCurrentResource(resource);
    }
}
