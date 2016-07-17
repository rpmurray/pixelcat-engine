package info.masterfrog.pixelcat.engine.logic.clock;

abstract class GameClockImpl implements GameClock {
    private final Long epochTime;

    GameClockImpl() {
        epochTime = getRawTimer();
    }

    protected Long getEpochTime() {
        return epochTime;
    }

    protected static Long getRawTimer() {
        return System.nanoTime();
    }

    protected Long getNormalizedTimer() {
        return getRawTimer() - epochTime;
    }

    public Long denormalizeTimer(Long timer) {
        // generate denormalized timer
        Long denormalizedTimer = timer + epochTime;

        return denormalizedTimer;
    }

    public Long getDelta(Long timer1, Long timer2) {
        return timer2 - timer1;
    }

    public Long getDelta(GameClock clock1, GameClock clock2) {
        return getDelta(clock1.denormalizeTimer(clock1.getOriginTimer()), clock2.denormalizeTimer(clock2.getOriginTimer()));
    }
}
