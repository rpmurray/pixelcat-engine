package com.rpm.pixelcat.engine.logic.clock;

public interface GameClock {
    public Long denormalizeTimer(Long timer);

    public Long getOriginTimer();

    public Long getDelta(Long timer1, Long timer2);

    public Long getDelta(GameClock clock1, GameClock clock2);

    public static Long toMS(Long ns) {
        return ns / 1000000;
    }
}
