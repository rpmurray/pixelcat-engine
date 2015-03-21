package com.rpm.pixelcat.engine.logic.clock;

public class GameClockEventImpl implements GameClockEvent {
    private Long timer;
    private String tag;

    GameClockEventImpl(Long timer, String tag) {
        this.timer = timer;
        this.tag = tag;
    }

    public Long getTimer() {
        return timer;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "GameClockEvent{" +
            "timer=" + timer +
            ", tag='" + tag + '\'' +
            '}';
    }
}
