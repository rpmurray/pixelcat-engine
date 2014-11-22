package com.rpm.pixelcat.logic.resource;

import com.rpm.pixelcat.constants.ResourceConstants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class ResourceFileLoaderImpl implements ResourceFileLoader {
    public BufferedImage loadImage(String fileName) throws IOException, URISyntaxException {
        // set up file path
        String filePath = ResourceConstants.IMAGE_RESOURCE_PATH.getValue() + "/" + fileName;
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePath);
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        return bufferedImage;
    }
}
