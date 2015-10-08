package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.logic.common.IdGeneratorImpl;

import java.awt.*;

class TextResourceImpl extends ResourceImpl implements TextResource {
    String text;
    Font font;

    TextResourceImpl(String text, Font font) {
        super(TextResource.class.getSimpleName());
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
