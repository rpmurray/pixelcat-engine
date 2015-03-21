package com.rpm.pixelcat.engine.logic.clock;

public class GameClockFactory {
    private static GameClockFactory instance;

    public static GameClockFactory getInstance() {
        if (instance == null) {
            instance = new GameClockFactory();
        }

        return instance;
    }

    public GameClockManager createGameClockManager() {
        GameClockManager gameClockManager = new GameClockManagerImpl();

        return gameClockManager;
    }

    public GameClock createGameClock() {
        GameClock gameClock = new GameClockImpl();

        return gameClock;
    }
}
