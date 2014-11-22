package com.rpm.pixelcat.kernel;

import com.rpm.pixelcat.logic.resource.model.ImageResource;
import com.rpm.pixelcat.logic.resource.model.Resource;
import com.rpm.pixelcat.logic.resource.model.TextResource;

import java.awt.*;

public class Renderer {
    void render(Graphics2D g, KernelState kernelState, Resource[] objects) {
        // Clear the drawing area, then draw logic components
        Rectangle bounds = kernelState.getBounds();
        g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);

        for (Resource object : objects) {
            if (object instanceof ImageResource) {
                ImageResource imageObject = (ImageResource) object;
                g.drawImage(
                    imageObject.getImage(),
                    (int) imageObject.getX(), (int) imageObject.getY(),
                    (int) (imageObject.getX() + imageObject.getWidth()), (int) (imageObject.getY() + imageObject.getHeight()),
                    (int) imageObject.getImageX1(), (int) imageObject.getImageY1(),
                    (int) imageObject.getImageX2(), (int) imageObject.getImageY2(),
                    null
                );
            } else if (object instanceof TextResource) {
                TextResource textObject = (TextResource) object;
                g.setFont(new Font(textObject.getFont().getFamily(), textObject.getFont().getStyle(), textObject.getFont().getSize()));
                g.drawString(
                    textObject.getText(), (int) textObject.getX(), (int) textObject.getY()
                );
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
