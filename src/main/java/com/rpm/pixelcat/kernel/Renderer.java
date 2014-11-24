package com.rpm.pixelcat.kernel;

import com.rpm.pixelcat.common.Printer;
import com.rpm.pixelcat.exception.GameErrorCode;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.logic.gameobject.GameObject;
import com.rpm.pixelcat.logic.resource.model.ImageResource;
import com.rpm.pixelcat.logic.resource.model.Resource;
import com.rpm.pixelcat.logic.resource.model.TextResource;

import java.awt.*;

public class Renderer {
    private static final Printer PRINTER = new Printer();

    void render(Graphics2D g, KernelState kernelState, GameObject[][] layeredGameObjects) {
        // Clear the drawing area, then draw logic components
        Rectangle bounds = kernelState.getBounds();
        g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);

        for (GameObject[] gameObjects: layeredGameObjects) {
            for (GameObject gameObject : gameObjects) {
                // check for empty item
                if (gameObject == null) {
                    continue;
                }

                // setup
                Point position = gameObject.getPosition();
                Resource resource = gameObject.getCurrentResource();

                // render specific resource by type
                if (resource instanceof ImageResource) {
                    // setup
                    ImageResource imageResource = (ImageResource) resource;

                    // load resource if needed
                    if (!imageResource.isImageLoaded()) {
                        try {
                            imageResource.loadImage();
                        } catch (GameException e) {
                            PRINTER.printError(e);
                        }
                    }

                    // render
                    g.drawImage(
                        imageResource.getImage(),
                        (int) position.getX(), (int) position.getY(),
                        (int) (position.getX() + imageResource.getWidth()), (int) (position.getY() + imageResource.getHeight()),
                        (int) imageResource.getImageX1(), (int) imageResource.getImageY1(),
                        (int) imageResource.getImageX2(), (int) imageResource.getImageY2(),
                        null
                    );
                    PRINTER.printDebug("Rendering image -> " + imageResource + " L" + gameObject.getLayer() + "@[" + position.getX() + "," + position.getY() + "]");
                } else if (resource instanceof TextResource) {
                    // setup
                    TextResource textResource = (TextResource) resource;

                    // render
                    g.setFont(new Font(textResource.getFont().getFamily(), textResource.getFont().getStyle(), textResource.getFont().getSize()));
                    g.drawString(
                        textResource.getText(), (int) position.getX(), (int) position.getY()
                    );
                    PRINTER.printDebug("Rendering text -> " + textResource + " L" + gameObject.getLayer() + "@[" + position.getX() + "," + position.getY() + "]");
                } else {
                    PRINTER.printError(new GameException(GameErrorCode.UNSUPPORTED_RESOURCE_FOR_RENDERING, resource));
                }
            }
        }

        // font rendering -- debug
        if (kernelState.getPropertyAsBoolean(KernelStatePropertyEnum.FONT_DEBUG_ENABLED)) {
            debugFonts(g, kernelState.getBounds());
        }
    }

    private void debugFonts(Graphics2D g, Rectangle bounds) {
        int x = bounds.x;
        int y = bounds.y;
        for (String fontName : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            y += 20;
            if (y > bounds.x + bounds.height) {
                y = bounds.y + 20;
                x += 250;
            }
            g.setFont(new Font(fontName, Font.PLAIN, 16));
            g.drawString(fontName, x, y);
        }
    }
}
