package com.rpm.pixelcat.engine.logic.clock;

class AdvancedGameClockImpl extends EventDrivenGameClockImpl implements AdvancedGameClock {
    AdvancedGameClockImpl() {
        super();
    }

    @Override
    public String toString() {
        return "AdvancedGameClockImpl{}" +
        "epochTime=" + getEpochTime() +
            ", events=" + getAllEvents() +
            '}';
    }
}
