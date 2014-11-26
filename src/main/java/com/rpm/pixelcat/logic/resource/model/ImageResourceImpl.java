package com.rpm.pixelcat.logic.resource.model;

import com.rpm.pixelcat.exception.GameErrorCode;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.logic.resource.ResourceFileLoader;

import java.awt.image.BufferedImage;

class ImageResourceImpl extends ResourceImpl implements ImageResource {
    String file;
    BufferedImage image;
    Integer imageX1, imageX2, imageY1, imageY2;

    ImageResourceImpl(String file, Integer imageX1, Integer imageY1, Integer imageX2, Integer imageY2) {
        super(imageX2 - imageX1, imageY2 - imageY1);
        this.file = file;
        this.imageX1 = imageX1;
        this.imageX2 = imageX2;
        this.imageY1 = imageY1;
        this.imageY2 = imageY2;
    }

    public Boolean isImageLoaded() {
        return image != null;
    }

    public void loadImage() throws GameException {
        try {
            this.image = ResourceFileLoader.getInstance().loadImage(file);
        } catch (Exception e) {
            throw new GameException(GameErrorCode.INTERNAL_ERROR, e);
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public double getImageX1() {
        return imageX1;
    }

    public double getImageX2() {
        return imageX2;
    }

    public double getImageY1() {
        return imageY1;
    }

    public double getImageY2() {
        return imageY2;
    }

    @Override
    public String toString() {
        return "ImageObject{" +
            "image=" + image +
            "file=" + file +
            ", imageX1=" + imageX1 +
            ", imageX2=" + imageX2 +
            ", imageY1=" + imageY1 +
            ", imageY2=" + imageY2 +
            '}';
    }
}
