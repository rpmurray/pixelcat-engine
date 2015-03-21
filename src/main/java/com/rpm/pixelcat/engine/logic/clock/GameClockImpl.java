package com.rpm.pixelcat.engine.logic.clock;

import java.util.ArrayList;
import java.util.List;

public class GameClockImpl implements GameClock {
    private final Long epochTime;
    private List<GameClockEvent> events;

    GameClockImpl() {
        epochTime = System.currentTimeMillis();
        events = new ArrayList<>();
        events.add(new GameClockEventImpl(0L, TIMER_EVENT_GAME_CLOCK_ORIGIN));
    }

    public GameClockEvent addEvent(String tag) {
        // get time
        Long timer = getTimer();

        // create event
        GameClockEvent event = new GameClockEventImpl(timer, tag);

        // record event
        events.add(event);

        return event;
    }

    public Long getTimer() {
        return System.currentTimeMillis() - epochTime;
    }

    public Long normalizeTimer(Long timer) {
        // generate normalized time
        Long normalizedTime = timer + epochTime;

        return normalizedTime;
    }

    public Long getDelta(Long timer1, Long timer2) {
        return timer2 - timer1;
    }

    public Long getDelta(GameClock clock1, GameClock clock2) {
        return getDelta(clock1.getTimer(), clock2.getTimer());
    }

    public Long getDelta(GameClock clock1, Long timer2) {
        return getDelta(clock1.getTimer(), timer2);
    }

    public Long getDelta(Long timer1, GameClock clock2) {
        return getDelta(timer1, clock2.getTimer());
    }

    public GameClockEvent getEvent(Integer index) {
        // validation
        if (index < 0 || index >= events.size()) {
            index = 0;
        }

        return events.get(index);
    }

    public GameClockEvent getOriginEvent() {
        return events.get(0);
    }

    public List<GameClockEvent> getEvents(String tag) {
        // setup
        List<GameClockEvent> foundEvents = new ArrayList<>();

        // find event
        for (GameClockEvent event: events) {
            if (event.getTag().equals(tag)) {
                foundEvents.add(event);
            }
        }

        return foundEvents;
    }

    public List<GameClockEvent> getEvents() {
        return events;
    }

    public Long getElapsed() {
        // get start + end timer
        Long startTimer = getEvent(getEventsCount() - 1).getTimer();
        Long endTimer = getTimer();

        return getDelta(startTimer, endTimer);
    }

    public Long getElapsedAndAddEvent(String tag) {
        // get start timer
        Long startTimer = getEvent(getEventsCount() - 1).getTimer();

        // add event
        GameClockEvent endEvent = addEvent(tag);

        // get end timer
        Long endTimer = endEvent.getTimer();

        return getDelta(startTimer, endTimer);
    }

    public Long getElapsed(String tag) {
        // get start + end timers
        List<GameClockEvent> startEvents = getEvents(tag);
        Long startTimer;
        if (startEvents.size() == 0) {
            return 0L;
        } else {
            startTimer = startEvents.get(startEvents.size() - 1).getTimer();
        }
        Long endTimer = getTimer();

        return getDelta(startTimer, endTimer);
    }

    public Long getElapsed(String tag1, String tag2) {
        // get timer1 + timer2
        List<GameClockEvent> events1 = getEvents(tag1);
        if (events1.size() == 0) {
            return 0L;
        }
        Long timer1 = events1.get(events1.size() - 1).getTimer();
        List<GameClockEvent> events2 = getEvents(tag1);
        if (events2.size() == 0) {
            return 0L;
        }
        Long timer2 = events1.get(events2.size() - 1).getTimer();

        return getDelta(timer1, timer2);
    }

    public Long getElapsed(Integer index1, Integer index2) {
        // get timer1 + timer2
        Long timer1 = getEvent(index1).getTimer();
        Long timer2 = getEvent(index2).getTimer();

        return getDelta(timer1, timer2);
    }

    public Integer getEventsCount() {
        return events.size();
    }
}
