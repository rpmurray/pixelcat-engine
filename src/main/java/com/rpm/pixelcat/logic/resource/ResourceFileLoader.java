package com.rpm.pixelcat.logic.resource;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

public interface ResourceFileLoader {
    public BufferedImage loadImage(String file) throws IOException, URISyntaxException;
}
