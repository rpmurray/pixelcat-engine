package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.GameException;

import java.awt.*;

public class PackagedBasicFontResourceImpl implements BasicFontResource, LoadableResource {
    private String fileName;
    private Font font;

    PackagedBasicFontResourceImpl(String fileName) {
        this.fileName = fileName;
    }

    public Boolean isLoaded() {
        return font != null;
    }

    public void load() throws GameException {
        if (font == null) {
            font = FileLoader.getInstance().loadFont(fileName);
        }
    }

    public Font getFont() {
        return font;
    }

    @Override
    public String toString() {
        return "PackagedBasicFontResourceImpl{" +
            "fileName='" + fileName + '\'' +
            ", font=" + font +
            '}';
    }
}
