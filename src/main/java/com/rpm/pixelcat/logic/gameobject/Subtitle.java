package com.rpm.pixelcat.logic.gameobject;

import com.rpm.pixelcat.logic.resource.model.TextResource;
import com.rpm.pixelcat.logic.resource.model.TextResourceImpl;

import java.awt.*;

public class Subtitle extends GameObject {
    private TextResource resource;

    Subtitle(Rectangle bounds) {
        resource = new TextResourceImpl(
            bounds.width / 2 - 200, bounds.height / 2 - 30,
            "The 2D Sprite Base Video Game Engine", new Font("Courier New", Font.BOLD, 20)
        );
    }
}
