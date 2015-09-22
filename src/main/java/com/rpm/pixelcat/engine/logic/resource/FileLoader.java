package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.constants.ResourceConstants;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class FileLoader {
    private static FileLoader instance;

    private FileLoader() {
        // do nothing
    }

    public static FileLoader getInstance() {
        if (instance == null) {
            instance = new FileLoader();
        }

        return instance;
    }

    public BufferedImage loadImage(String fileName) throws GameException {
        // setup
        BufferedImage bufferedImage;

        // load file
        try {
            String filePath = ResourceConstants.IMAGE_RESOURCE_PATH.getValue() + "/" + fileName;
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePath);
            bufferedImage = ImageIO.read(inputStream);
        } catch (Exception e) {
            throw new GameException(GameErrorCode.INTERNAL_ERROR, e);
        }

        return bufferedImage;
    }

    public Font loadFont(String fileName) throws GameException {
        Font font;

        try {
            String filePath = ResourceConstants.FONT_RESOURCE_PATH.getValue() + "/" + fileName;
            font = Font.createFont(Font.TRUETYPE_FONT, new File(filePath));
        } catch (Exception e) {
            throw new GameException(GameErrorCode.INTERNAL_ERROR, e);
        }

        return font;
    }
}
