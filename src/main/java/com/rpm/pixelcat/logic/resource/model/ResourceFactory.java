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

    public ImageResource createImageResource(Integer x, Integer y, String file, Integer imageX1, Integer imageY1, Integer imageX2, Integer imageY2) {
        ImageResource resource = new ImageResourceImpl(x, y, file, imageX1, imageY1, imageX2, imageY2);

        return resource;
    }

    public TextResource createTextResource(Integer x, Integer y, String text, Font font) {
        TextResource resource = new TextResourceImpl(x, y, text, font);

        return resource;
    }
}
