package com.rpm.pixelcat.engine.logic.clock;

import com.rpm.pixelcat.engine.exception.GameException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GameClock {
    public static final String TIMER_EVENT_GAME_CLOCK_ORIGIN = "game clock creation";

    public GameClockEvent addEvent(String tag);

    public Long denormalizeTimer(Long timer);

    public Long getDelta(Long timer1, Long timer2);

    public Long getDelta(GameClock clock1, GameClock clock2);

    public Long getDelta(GameClock clock1, Long timer2);

    public Long getDelta(Long timer1, GameClock clock2);

    public GameClockEvent getTagEvent(String tag, Integer index) throws GameException;

    public Long getTagTimer(String tag, Integer index) throws GameException;

    public GameClockEvent getMostRecentTagEvent(String tag) throws GameException;

    public Long getMostRecentTagTimer(String tag) throws GameException;

    public GameClockEvent getOriginEvent();

    public Long getOriginTimer();

    public List<GameClockEvent> getTagEvents(String tag);

    public List<Long> getTagTimers(String tag);

    public Map<String, List<GameClockEvent>> getAllEvents();

    public Map<String, List<Long>> getAllTimers();

    public Long getElapsedAndAddEvent(String tag) throws GameException;

    public Long getElapsed(String tag) throws GameException;

    public Long getElapsed(String tag1, String tag2) throws GameException;

    public Long getElapsed(String tag1, Integer index1, String tag2, Integer index2) throws GameException;

    public Set<String> getTags();

    public Integer getCount();

    public static Long toMS(Long ns) {
        return ns / 1000000;
    }
}
