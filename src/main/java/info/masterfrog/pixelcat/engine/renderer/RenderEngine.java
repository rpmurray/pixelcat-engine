package info.masterfrog.pixelcat.engine.renderer;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.kernel.CanvasManager;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.kernel.KernelStateProperty;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.canvas.Canvas;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.rendering.Rendering;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.RenderingLibrary;
import info.masterfrog.pixelcat.engine.logic.resource.ImageResource;
import info.masterfrog.pixelcat.engine.logic.resource.SpriteResource;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.object.GameObject;
import info.masterfrog.pixelcat.engine.logic.resource.Resource;
import info.masterfrog.pixelcat.engine.logic.resource.TextResource;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public class RenderEngine {
    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(RenderEngine.class);

    public void process(Graphics2D g, List<List<GameObject>> layeredGameObjects) throws TransientGameException {
        // Clear the drawing area, then draw logic components
        Rectangle bounds = KernelState.getInstance().getProperty(KernelStateProperty.SCREEN_BOUNDS);
        g.setBackground(KernelState.getInstance().getProperty(KernelStateProperty.BACKGROUND_COLOR));
        g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);

        for (List<GameObject> gameObjects: layeredGameObjects) {
            for (GameObject gameObject : gameObjects) {
                // check for empty item
                if (gameObject == null) {
                    continue;
                }

                // check for non-renderable object
                if (!gameObject.isFeatureAvailable(RenderingLibrary.class)) {
                    continue;
                }

                // setup
                Rendering rendering = gameObject.getFeature(RenderingLibrary.class).getCurrent(gameObject.getCanvas());
                Canvas canvas = rendering.getCanvas();
                Point position = new Point(
                    canvas.getPosition().x + rendering.getCanvasNormalizedPosition().x,
                    canvas.getPosition().y + rendering.getCanvasNormalizedPosition().y
                );
                Double scaleFactor = canvas.getScaleFactor() * rendering.getScaleFactor();
                Integer layer = rendering.getLayer();
                Resource resource = rendering.getRenderableResource(gameObject);

                // see if rendering position is off the canvas, if so skip processing
                if (rendering.getPosition().x > canvas.getBounds().width || rendering.getPosition().y > canvas.getBounds().height) {
                    continue;
                }

                // see if canvas is active, if not skip processing
                CanvasManager canvasManager = KernelState.getInstance().getProperty(KernelStateProperty.CANVAS_MANAGER);
                if (!canvasManager.has(canvas.getId()) ||
                    !canvasManager.isActive(canvas.getId())) {
                    continue;
                }

                // render specific resource by type
                if (resource instanceof ImageResource) {
                    renderSpriteResource(g, ((ImageResource) resource).getMainResource(), position, layer, scaleFactor);
                } else if (resource instanceof SpriteResource) {
                    renderSpriteResource(g, (SpriteResource) resource, position, layer, scaleFactor);
                } else if (resource instanceof TextResource) {
                    renderTextResource(g, (TextResource) resource, position, layer);
                } else {
                    PRINTER.printError(new TransientGameException(GameEngineErrorCode.UNSUPPORTED_RESOURCE_FOR_RENDERING, resource));
                }
            }
        }

        // font rendering -- debug
        if (KernelState.getInstance().getPropertyFlag(KernelStateProperty.FONT_DISPLAY_ENABLED)) {
            debugFonts(g, KernelState.getInstance().getProperty(KernelStateProperty.SCREEN_BOUNDS));
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

        // set up alpha mask composite
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, spriteResource.getAlphaMask()));

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
