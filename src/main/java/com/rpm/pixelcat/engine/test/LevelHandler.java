package com.rpm.pixelcat.engine.test;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class LevelHandler {
    private String currentLevel;

    public static final String START_SCREEN = "start screen";
    public static final String LEVEL_ONE = "level one";

    private static final Map<String, String> LEVEL_TRANSITIONS = ImmutableMap.of(
        START_SCREEN, LEVEL_ONE
    );


    public LevelHandler(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public String getNextLevel() {
        // check for level transition
        if (!LEVEL_TRANSITIONS.containsKey(currentLevel)) {
            return null;
        }

        // update current level
        currentLevel = LEVEL_TRANSITIONS.get(currentLevel);

        return currentLevel;
    }
}
