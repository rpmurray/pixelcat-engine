package com.rpm.pixelcat.engine.logic.resource;

import java.awt.*;

class TextResourceImpl implements TextResource {
    String text;
    Font font;

    TextResourceImpl(String text, Font font) {
        this.text = text;
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public Font getFont() {
        return font;
    }

    @Override
    public String toString() {
        return "TextResourceImpl{" +
            "text='" + text + '\'' +
            ", font=" + font +
            '}';
    }
}
