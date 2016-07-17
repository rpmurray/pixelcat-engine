package info.masterfrog.pixelcat.engine.logic.resource;

import java.awt.*;

public class BuiltInBasicFontResourceImpl implements BasicFontResource {
    private Font font;

    BuiltInBasicFontResourceImpl(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    @Override
    public String toString() {
        return "BuiltInBasicFontResourceImpl{" +
            "font=" + font +
            '}';
    }
}
