package com.rpm.pixelcat.engine.logic.clock;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.TransientGameException;

import java.util.*;

abstract class EventDrivenGameClockImpl extends GameClockImpl implements EventDrivenGameClock {
    private Map<String, List<Long>> events;

    EventDrivenGameClockImpl() {
        super();
        events = new HashMap<>();
        events.put(TIMER_EVENT_GAME_CLOCK_ORIGIN, new ArrayList<>());
        events.get(TIMER_EVENT_GAME_CLOCK_ORIGIN).add(0L);
    }

    public GameClockEvent addEvent(String tag) {
        // get time
        Long timer = getNormalizedTimer();

        // create tag if it doesn't exist
        if (!events.containsKey(tag)) {
            events.put(tag, new ArrayList<>());
        }

        // record event
        events.get(tag).add(timer);

        // create game clock event for return
        GameClockEvent event = new GameClockEventImpl(tag, timer);

        return event;
    }

    public GameClockEvent getTagEvent(String tag, Integer index) throws TransientGameException {
        // create game clock event for return
        GameClockEvent event = new GameClockEventImpl(tag, getTagTimer(tag, index));

        return event;
    }

    public Long getTagTimer(String tag, Integer index) throws TransientGameException {
        // validation
        if (!events.containsKey(tag)) {
            throw new TransientGameException(GameErrorCode.GAME_CLOCK_ERROR);
        }
        if (index < 0 || index >= events.get(tag).size()) {
            throw new TransientGameException(GameErrorCode.GAME_CLOCK_ERROR);
        }

        return events.get(tag).get(index);
    }

    public GameClockEvent getMostRecentTagEvent(String tag) throws TransientGameException {
        // create game clock event for return
        GameClockEvent event = new GameClockEventImpl(tag, getMostRecentTagTimer(tag));

        return event;
    }

    public Long getMostRecentTagTimer(String tag) throws TransientGameException {
        // validation
        if (!events.containsKey(tag)) {
            throw new TransientGameException(GameErrorCode.GAME_CLOCK_ERROR);
        }

        // get timer events for tag
        List<Long> tagEvents = events.get(tag);

        // validation
        if (tagEvents.size() == 0) {
            throw new TransientGameException(GameErrorCode.GAME_CLOCK_ERROR);
        }

        return tagEvents.get(tagEvents.size() - 1);
    }

    public GameClockEvent getOriginEvent() {
        // create game clock event for return
        GameClockEvent event = new GameClockEventImpl(TIMER_EVENT_GAME_CLOCK_ORIGIN, getOriginTimer());

        return event;
    }

    public Long getOriginTimer() {
        // create game clock event for return
        Long timer = events.get(TIMER_EVENT_GAME_CLOCK_ORIGIN).get(0);

        return timer;
    }

    public List<GameClockEvent> getTagEvents(String tag) {
        // setup
        List<Long> timerEvents = getTagTimers(tag);

        // create game clock events for return
        List<GameClockEvent> gameClockEvents = new ArrayList<>();
        for (Long timer: timerEvents) {
            gameClockEvents.add(new GameClockEventImpl(tag, timer));
        }

        return gameClockEvents;
    }

    public List<Long> getTagTimers(String tag) {
        return events.get(tag);
    }

    public Map<String, List<GameClockEvent>> getAllEvents() {
        // setup
        Map<String, List<GameClockEvent>> eventsMap = new HashMap<>();

        // loop through all tags and build events
        for (String tag: events.keySet()) {
            eventsMap.put(tag, getTagEvents(tag));
        }

        return eventsMap;
    }

    public Map<String, List<Long>> getAllTimers() {
         return events;
    }

    public Long getElapsedAndAddEvent(String tag) throws TransientGameException {
        // get start timer
        List<Long> timers = getTagTimers(tag);
        Long startTimer = timers.get(timers.size() - 1);

        // add event
        GameClockEvent endEvent = addEvent(tag);

        // get end timer
        Long endTimer = endEvent.getTimer();

        return getDelta(startTimer, endTimer);
    }

    public Long getElapsed(String tag) throws TransientGameException {
        // get start + end timers
        List<Long> timers = getTagTimers(tag);
        Long startTimer;
        if (timers == null || timers.size() == 0) {
            throw new TransientGameException(GameErrorCode.GAME_CLOCK_TAG_NOT_EXISTS);
        } else {
            startTimer = timers.get(timers.size() - 1);
        }
        Long endTimer = getNormalizedTimer();

        return getDelta(startTimer, endTimer);
    }

    public Long getElapsed(String tag1, String tag2) throws TransientGameException {
        // get timer1 + timer2
        Long timer1 = getMostRecentTagTimer(tag1);
        Long timer2 = getMostRecentTagTimer(tag2);

        return getDelta(timer1, timer2);
    }

    public Long getElapsed(String tag1, Integer index1, String tag2, Integer index2) throws TransientGameException {
        // get timer1 + timer2
        Long timer1 = getTagTimer(tag1, index1);
        Long timer2 = getTagTimer(tag2, index2);

        return getDelta(timer1, timer2);
    }

    public Set<String> getTags() {
        return events.keySet();
    }

    public Integer getCount() {
        Integer count = 0;
        for(String tag: events.keySet()) {
            count += events.get(tag).size();
        }

        return count;
    }

    @Override
    public String toString() {
        return "EventDrivenGameClockImpl{" +
            "epochTime=" + getEpochTime() +
            ", events=" + events +
            '}';
    }
}
