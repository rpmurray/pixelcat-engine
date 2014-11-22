package com.rpm.pixelcat.logic.resource.model;

import com.rpm.pixelcat.exception.GameException;

import java.awt.image.BufferedImage;

public interface ImageResource extends Resource
{
    public Boolean isImageLoaded();

    public void loadImage() throws GameException;

    public BufferedImage getImage();

    public double getWidth();

    public double getHeight();

    public double getImageX1();

    public double getImageX2();

    public double getImageY1();

    public double getImageY2();
}
