package com.rpm.pixelcat.engine.common.file;

import com.rpm.pixelcat.engine.constants.ResourceType;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.TransientGameException;
import org.w3c.dom.Document;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
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

    public BufferedImage loadImage(String fileName) throws TransientGameException {
        // setup
        BufferedImage bufferedImage;

        // load file
        try {
            InputStream inputStream = createInputStream(fileName, ResourceType.IMAGE);
            bufferedImage = ImageIO.read(inputStream);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.INTERNAL_ERROR, e);
        }

        return bufferedImage;
    }

    public Font loadFont(String fileName) throws TransientGameException {
        Font font;

        try {
            InputStream inputStream = createInputStream(fileName, ResourceType.FONT);
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.INTERNAL_ERROR, e);
        }

        return font;
    }

    public Document loadXmlFile(String fileName) throws TransientGameException {
        Document document;

        try {
            InputStream inputStream = FileLoader.getInstance().createInputStream(fileName, ResourceType.XML);
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.XML_PARSER_ERROR, e);
        }

        return document;
    }

    private InputStream createInputStream(String fileName, ResourceType resourceType) throws TransientGameException {
        // setup
        InputStream inputStream;

        // load file
        try {
            String filePath = resourceType.getResourceFilePath() + "/" + fileName;
            inputStream = ClassLoader.getSystemResourceAsStream(filePath);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.INTERNAL_ERROR, e);
        }

        return inputStream;
    }
}
