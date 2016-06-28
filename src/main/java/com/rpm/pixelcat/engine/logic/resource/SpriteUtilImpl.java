package com.rpm.pixelcat.engine.logic.resource;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

class SpriteUtilImpl implements SpriteUtil {
    private static SpriteUtilImpl instance = null;

    // private constructor for singleton
    private SpriteUtilImpl() {
        // do nothing
    }

    static SpriteUtil getInstance() {
        if (instance == null) {
            instance = new SpriteUtilImpl();
        }

        return instance;
    }

    public int[][] convertBufferedImageToRaw2dArgb(BufferedImage bufferedImage) {
        // convert buffered image to flat pixel data array
        final byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();

        // setup
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final boolean hasAlphaChannel = bufferedImage.getAlphaRaster() != null;
        int[][] raw2DRepresentation = new int[height][width];
        final int pixelLength = hasAlphaChannel ? 4 : 3;

        // convert flat array to 2D array + compress pixel data one pixel at a time
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            // setup
            int argb = 0;

            // calculate argb value for pixel
            if (hasAlphaChannel) {
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
            } else {
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
            }
            raw2DRepresentation[row][col] = argb;

            // handle column/row increments
            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }

        return raw2DRepresentation;
    }

    public boolean[][] convertBufferedImageToRaw2dMask(BufferedImage bufferedImage) {
        // convert buffered image to flat pixel data array
        final byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();

        // setup
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final boolean hasAlphaChannel = bufferedImage.getAlphaRaster() != null;
        boolean[][] raw2DRepresentation = new boolean[height][width];
        final int pixelLength = hasAlphaChannel ? 4 : 3;

        // convert flat array to 2D array + compress pixel data one pixel at a time
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            // setup
            boolean mask;

            // calculate mask value for pixel
            if (hasAlphaChannel) {
                mask = pixels[pixel + 1] + pixels[pixel + 2] + pixels[pixel + 3] > ((byte) 0);
            } else {
                mask = pixels[pixel] + pixels[pixel + 1] + pixels[pixel + 2] > ((byte) 0);
            }
            raw2DRepresentation[row][col] = mask;

            // handle column/row increments
            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }

        return raw2DRepresentation;
    }
}
