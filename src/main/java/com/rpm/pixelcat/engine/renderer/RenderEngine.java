package com.rpm.pixelcat.engine.renderer;

import com.rpm.pixelcat.engine.common.printer.Printer;
import com.rpm.pixelcat.engine.common.printer.PrinterFactory;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.kernel.KernelStatePropertyEnum;
import com.rpm.pixelcat.engine.logic.gameobject.feature.Renderable;
import com.rpm.pixelcat.engine.logic.resource.ImageResource;
import com.rpm.pixelcat.engine.logic.resource.SpriteResource;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.gameobject.GameObject;
import com.rpm.pixelcat.engine.logic.resource.Resource;
import com.rpm.pixelcat.engine.logic.resource.TextResource;

import java.awt.*;
import java.util.List;

public class RenderEngine {
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(RenderEngine.class);

    public void process(Graphics2D g, KernelState kernelState, List<List<GameObject>> layeredGameObjects) throws TransientGameException {
        // Clear the drawing area, then draw logic components
        Rectangle bounds = (Rectangle) kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS);
        g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);

        for (List<GameObject> gameObjects: layeredGameObjects) {
            for (GameObject gameObject : gameObjects) {
                // check for empty item
                if (gameObject == null) {
                    continue;
                }

                // check for non-renderable object
                if (!gameObject.isFeatureAvailable(Renderable.class)) {
                    continue;
                }

                // setup
                Renderable renderFeature = gameObject.getFeature(Renderable.class);
                Point position = renderFeature.getPosition();
                Double scaleFactor = renderFeature.getScaleFactor();
                Integer layer = renderFeature.getLayer();
                Resource resource = renderFeature.getRenderableResource(gameObject);

                // render specific resource by type
                if (resource instanceof ImageResource) {
                    renderSpriteResource(g, ((ImageResource) resource).getMainResource(), position, layer, scaleFactor);
                } else if (resource instanceof SpriteResource) {
                    renderSpriteResource(g, (SpriteResource) resource, position, layer, scaleFactor);
                } else if (resource instanceof TextResource) {
                    renderTextResource(g, (TextResource) resource, position, layer);
                } else {
                    PRINTER.printError(new TransientGameException(GameErrorCode.UNSUPPORTED_RESOURCE_FOR_RENDERING, resource));
                }
            }
        }

        // font rendering -- debug
        if (kernelState.getPropertyFlag(KernelStatePropertyEnum.FONT_DISPLAY_ENABLED)) {
            debugFonts(g, (Rectangle) kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS));
        }
    }

    private void renderSpriteResource(Graphics2D g, SpriteResource spriteResource, Point position, Integer layer, Double scaleFactor) {
        // setup
        Rectangle spriteBounds = spriteResource.getCelBounds();

        // load resource if needed
        if (!spriteResource.isLoaded()) {
            try {
                spriteResource.load();
            } catch (TransientGameException e) {
                PRINTER.printError(e);
            }
        }

        // draw
        g.drawImage(
            spriteResource.getSpriteSheet().getTexture(),
            (int) position.getX(),
            (int) position.getY(),
            (int) (position.getX() + (spriteBounds.getWidth() * scaleFactor)),
            (int) (position.getY() + (spriteBounds.getHeight() * scaleFactor)),
            (int) spriteBounds.getX(),
            (int) spriteBounds.getY(),
            (int) (spriteBounds.getX() + (spriteBounds.getWidth())),
            (int) (spriteBounds.getY() + (spriteBounds.getHeight())),
            null
        );

        // debug
        PRINTER.printTrace(
            "Rendering image -> " + spriteResource +
            " L" + layer +
            "@(" + (int) spriteBounds.getWidth() + "," + (int) spriteBounds.getHeight() + ")" +
            ":{" + (int) spriteBounds.getX() + ":" + (int) (spriteBounds.getX() + spriteBounds.getWidth()) +
            "," + (int) spriteBounds.getY() + ":" + (int) (spriteBounds.getY() + spriteBounds.getHeight()) + "}" +
            ":[" + (int) position.getX() + ":" + (int) (position.getX() + spriteBounds.getWidth()) +
            ":" + (int) position.getY() + ":" + (int) (position.getY() + spriteBounds.getHeight()) + "]"
        );
    }

    private void renderTextResource(Graphics2D g, TextResource textResource, Point position, Integer layer) {
        // set font
        g.setFont(
            new Font(
                textResource.getFont().getFamily(),
                textResource.getFont().getStyle(),
                textResource.getFont().getSize()
            )
        );

        // draw
        g.drawString(
            textResource.getText(),
            (int) position.getX(),
            (int) position.getY()
        );

        // debug
        PRINTER.printTrace(
            "Rendering text -> " + textResource +
            " L" + layer +
            "@{" + textResource.getText().length() + "}" +
            ":[" + position.getX() + "," + position.getY() + "]"
        );
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