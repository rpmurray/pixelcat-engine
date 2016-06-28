package com.rpm.pixelcat.engine.logic.clock;

public class SimpleGameClockImpl extends GameClockImpl implements GameClock, SimpleGameClock {
    private Long timer;

    SimpleGameClockImpl() {
        super();
        timer = 0L;
    }

    public Long getOriginTimer() {
        // spoof an origin event
        Long timer = 0L;

        return timer;
    }

    public Long getElapsed() {
        // setup
        Long startTimer = timer;
        Long endTimer = getNormalizedTimer();

        return getDelta(startTimer, endTimer);
    }

    public void reset() {
        timer = getNormalizedTimer();
    }

    @Override
    public String toString() {
        return "SimpleGameClockImpl{" +
            "epochTime=" + getEpochTime() +
            ", timer=" + timer +
            '}';
    }
}
