package com.rpm.pixelcat.logic.resource.model;

import java.awt.*;

public class ResourceFactory {
    private static ResourceFactory instance;

    private ResourceFactory() {
        // do nothing
    }

    public static ResourceFactory getInstance() {
        if (instance == null) {
            instance = new ResourceFactory();
        }

        return instance;
    }

    public ImageResource createImageResource(String file, Integer imageX1, Integer imageY1, Integer imageX2, Integer imageY2) {
        ImageResource resource = new ImageResourceImpl(file, imageX1, imageY1, imageX2, imageY2);

        return resource;
    }

    public TextResource createTextResource(String text, Font font) {
        TextResource resource = new TextResourceImpl(text, font);

        return resource;
    }
}
