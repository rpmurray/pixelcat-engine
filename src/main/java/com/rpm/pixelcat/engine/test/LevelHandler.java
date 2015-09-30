package com.rpm.pixelcat.engine.test;

import com.google.common.collect.ImmutableMap;
import com.rpm.pixelcat.engine.test.enumeration.LevelHandle;

import java.util.Map;

public class LevelHandler {
    private LevelHandle currentLevel;

    public static final String START_SCREEN = "start screen";
    public static final String LEVEL_ONE = "level one";

    private static final Map<LevelHandle, LevelHandle> LEVEL_TRANSITIONS = ImmutableMap.of(
        LevelHandle.START_SCREEN, LevelHandle.L1
    );


    public LevelHandler(LevelHandle currentLevel) {
        this.currentLevel = currentLevel;
    }

    public LevelHandle getCurrentLevel() {
        return currentLevel;
    }

    public LevelHandle getNextLevel() {
        // check for level transition
        if (!LEVEL_TRANSITIONS.containsKey(currentLevel)) {
            return null;
        }

        // update current level
        currentLevel = LEVEL_TRANSITIONS.get(currentLevel);

        return currentLevel;
    }
}
