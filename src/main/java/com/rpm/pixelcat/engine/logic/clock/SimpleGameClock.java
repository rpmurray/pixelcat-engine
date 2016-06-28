package com.rpm.pixelcat.engine.logic.clock;

public interface SimpleGameClock extends GameClock {
    Long getElapsed();

    void reset();
}
