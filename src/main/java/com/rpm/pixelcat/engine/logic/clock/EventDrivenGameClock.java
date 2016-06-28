package com.rpm.pixelcat.engine.logic.clock;

import com.rpm.pixelcat.engine.exception.TransientGameException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EventDrivenGameClock extends GameClock {
    public static final String TIMER_EVENT_GAME_CLOCK_ORIGIN = "game clock creation";

    public GameClockEvent addEvent(String tag);

    public GameClockEvent getTagEvent(String tag, Integer index) throws TransientGameException;

    public Long getTagTimer(String tag, Integer index) throws TransientGameException;

    public GameClockEvent getMostRecentTagEvent(String tag) throws TransientGameException;

    public Long getMostRecentTagTimer(String tag) throws TransientGameException;

    public GameClockEvent getOriginEvent();

    public List<GameClockEvent> getTagEvents(String tag);

    public List<Long> getTagTimers(String tag);

    public Map<String, List<GameClockEvent>> getAllEvents();

    public Map<String, List<Long>> getAllTimers();

    public Long getElapsedAndAddEvent(String tag) throws TransientGameException;

    public Long getElapsed(String tag) throws TransientGameException;

    public Long getElapsed(String tag1, String tag2) throws TransientGameException;

    public Long getElapsed(String tag1, Integer index1, String tag2, Integer index2) throws TransientGameException;

    public Set<String> getTags();

    public Integer getCount();
}
