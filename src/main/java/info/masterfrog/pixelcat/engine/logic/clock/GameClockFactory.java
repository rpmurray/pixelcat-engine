package info.masterfrog.pixelcat.engine.logic.clock;

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

    public SimpleGameClock createSimpleGameClock() {
        SimpleGameClock simpleGameClock = new SimpleGameClockImpl();

        return simpleGameClock;
    }

    public AdvancedGameClock createAdvancedGameClock() {
        AdvancedGameClock advancedGameClock = new AdvancedGameClockImpl();

        return advancedGameClock;
    }
}
