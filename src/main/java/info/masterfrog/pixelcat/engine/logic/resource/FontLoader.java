package info.masterfrog.pixelcat.engine.logic.resource;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.lang.System;

public class FontLoader {
    public void loadFont(String filename) {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(filename)));
        } catch (IOException | FontFormatException e) {
            //Handle exception
            System.out.print(e);
        }
    }
}