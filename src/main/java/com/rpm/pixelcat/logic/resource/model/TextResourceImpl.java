package com.rpm.pixelcat.logic.resource.model;

import java.awt.*;

class TextResourceImpl extends ResourceImpl implements TextResource {
    String text;
    Font font;

    TextResourceImpl(String text, Font font) {
        super(0, 0);
        this.text = text;
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public Font getFont() {
        return font;
    }
}
