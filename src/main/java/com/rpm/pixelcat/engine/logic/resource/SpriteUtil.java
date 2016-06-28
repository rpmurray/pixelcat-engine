package com.rpm.pixelcat.engine.logic.resource;

import java.awt.image.BufferedImage;

public interface SpriteUtil {
    int[][] convertBufferedImageToRaw2dArgb(BufferedImage bufferedImage);

    boolean[][] convertBufferedImageToRaw2dMask(BufferedImage bufferedImage);

    static SpriteUtil create() {
        SpriteUtil instance = SpriteUtilImpl.getInstance();

        return instance;
    }
}
