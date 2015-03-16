package com.rpm.pixelcat.kernel;

import com.rpm.pixelcat.common.Printer;
import com.rpm.pixelcat.exception.GameErrorCode;
import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.logic.gameobject.GameObject;
import com.rpm.pixelcat.logic.resource.SpriteResource;
import com.rpm.pixelcat.logic.resource.Resource;
import com.rpm.pixelcat.logic.resource.TextResource;

import java.awt.*;
import java.util.ArrayList;

public class Renderer {
    private static final Printer PRINTER = new Printer();

    void render(Graphics2D g, KernelState kernelState, ArrayList<ArrayList<GameObject>> layeredGameObjects) {
        // Clear the drawing area, then draw logic components
        Rectangle bounds = kernelState.getBounds();
        g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);

        for (ArrayList<GameObject> gameObjects: layeredGameObjects) {
            for (GameObject gameObject : gameObjects) {
                // check for empty item
                if (gameObject == null) {
                    continue;
                }

                // setup
                Point position = gameObject.getPosition();
                Resource resource = gameObject.getCurrentResource();

                // render specific resource by type
                if (resource instanceof SpriteResource) {
                    // setup
                    SpriteResource spriteResource = (SpriteResource) resource;
                    Rectangle spriteBounds = spriteResource.getCelBounds();

                    // load resource if needed
                    if (!spriteResource.isLoaded()) {
                        try {
                            spriteResource.load();
                        } catch (GameException e) {
                            PRINTER.printError(e);
                        }
                    }

                    // render
                    g.drawImage(
                        spriteResource.getSpriteSheet().getTexture(),
                        (int) position.getX(),
                        (int) position.getY(),
                        (int) (position.getX() + spriteBounds.getWidth()),
                        (int) (position.getY() + spriteBounds.getHeight()),
                        (int) spriteBounds.getX(),
                        (int) spriteBounds.getY(),
                        (int) (spriteBounds.getX() + spriteBounds.getWidth()),
                        (int) (spriteBounds.getY() + spriteBounds.getHeight()),
                        null
                    );
                    PRINTER.printDebug("Rendering image -> " + spriteResource + " L" + gameObject.getLayer() + "@[" + position.getX() + "," + position.getY() + "]");
                } else if (resource instanceof TextResource) {
                    // setup
                    TextResource textResource = (TextResource) resource;

                    // render
                    g.setFont(
                        new Font(
                            textResource.getFont().getFamily(),
                            textResource.getFont().getStyle(),
                            textResource.getFont().getSize()
                        )
                    );
                    g.drawString(
                        textResource.getText(),
                        (int) position.getX(),
                        (int) position.getY()
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
