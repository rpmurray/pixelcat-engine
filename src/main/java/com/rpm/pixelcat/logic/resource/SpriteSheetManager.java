package com.rpm.pixelcat.logic.resource;

import com.rpm.pixelcat.constants.SpriteSheetKeyEnum;
import com.rpm.pixelcat.logic.resource.model.SpriteSheet;

import java.util.HashMap;
import java.util.Map;

public class SpriteSheetManager {
    private Map<SpriteSheetKeyEnum, SpriteSheet> spriteSheets;

    private static SpriteSheetManager instance;

    private SpriteSheetManager() {
        spriteSheets = new HashMap<>();
    }

    public static SpriteSheetManager getInstance() {
        if (instance == null) {
            instance = new SpriteSheetManager();
        }

        return instance;
    }

    public void addSpriteSheet(SpriteSheetKeyEnum key, SpriteSheet spriteSheet) {
        spriteSheets.put(key, spriteSheet);
    }

    public SpriteSheet getSpriteSheet(SpriteSheetKeyEnum key) {
        return spriteSheets.get(key);
    }
}
