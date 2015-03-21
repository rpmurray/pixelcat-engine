package com.rpm.pixelcat.engine.logic.clock;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameClockManagerImpl implements GameClockManager {
    private Map<String, GameClock> gameClocks;

    GameClockManagerImpl() {
        this(new HashMap<>());
    }

    GameClockManagerImpl(String name, GameClock gameClock) {
        this(new HashMap(ImmutableMap.of(name, gameClock)));
    }

    GameClockManagerImpl(Map<String, GameClock> gameClocks) {
        this.gameClocks = gameClocks;
    }

    public void addGameClock(String name) {
        gameClocks.put(name, new GameClockImpl());
    }

    public void addGameClock(String name, GameClock gameClock) {
        gameClocks.put(name, gameClock);
    }

    public void addGameClocks(Map<String, GameClock> gameClocks) {
        this.gameClocks.putAll(gameClocks);
    }

    public GameClock getGameClock(String name) {
        return gameClocks.get(name);
    }

    public Integer getGameClocksCount() {
        return gameClocks.size();
    }

    public Set<String> getGameClockNames() {
        return gameClocks.keySet();
    }
}
