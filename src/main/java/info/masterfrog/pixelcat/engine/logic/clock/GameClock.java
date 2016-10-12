package info.masterfrog.pixelcat.engine.logic.clock;

public interface GameClock {
    Long denormalizeTimer(Long timer);

    Long getOriginTimer();

    Long getDelta(Long timer1, Long timer2);

    Long getDelta(GameClock clock1, GameClock clock2);

    static Long ns2ms(Long ns) {
        return ns / 1000000;
    }

    static Long ns2s(Long ns) {
        return ms2s(ns2ms(ns));
    }

    static Long ms2ns(Long ms) {
        return ms * 1000000;
    }

    static Long ms2s(Long ms) {
        return ms * 1000;
    }

    static Long s2ms(Long s) {
        return s / 1000;
    }

    static Long s2ns(Long s) {
        return ms2ns(s2ms(s));
    }
}
