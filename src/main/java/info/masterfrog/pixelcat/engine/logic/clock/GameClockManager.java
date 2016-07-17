package info.masterfrog.pixelcat.engine.logic.clock;

import java.util.Map;
import java.util.Set;

public interface GameClockManager {
    public void addSimpleGameClock(String name);

    public void addAdvancedGameClock(String name);

    public void addGameClock(String name, GameClock gameClock);

    public void addGameClocks(Map<String, GameClock> gameClocks);

    public GameClock getGameClock(String name);

    public Integer getGameClocksCount();

    public Set<String> getGameClockNames();
}
