package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.common.file.FileLoader;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.awt.Font;

public class PackagedBasicFontResourceImpl implements BasicFontResource, LoadableResource {
    private String fileName;
    private Font font;

    PackagedBasicFontResourceImpl(String fileName) {
        this.fileName = fileName;
    }

    public Boolean isLoaded() {
        return font != null;
    }

    public void load() throws TransientGameException {
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
