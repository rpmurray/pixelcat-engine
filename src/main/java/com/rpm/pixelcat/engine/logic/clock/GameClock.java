package com.rpm.pixelcat.engine.logic.clock;

import java.util.List;

public interface GameClock {
    public static final String TIMER_EVENT_GAME_CLOCK_ORIGIN = "game clock creation";

    public GameClockEvent addEvent(String tag);

    public Long getTimer();

    public Long normalizeTimer(Long timer);

    public Long getDelta(Long timer1, Long timer2);

    public Long getDelta(GameClock clock1, GameClock clock2);

    public Long getDelta(GameClock clock1, Long timer2);

    public Long getDelta(Long timer1, GameClock clock2);

    public GameClockEvent getEvent(Integer index);

    public GameClockEvent getOriginEvent();

    public List<GameClockEvent> getEvents(String tag);

    public List<GameClockEvent> getEvents();

    public Long getElapsed();

    public Long getElapsedAndAddEvent(String tag);

    public Long getElapsed(String tag);

    public Long getElapsed(String tag1, String tag2);

    public Long getElapsed(Integer index1, Integer index2);

    public Integer getEventsCount();
}
