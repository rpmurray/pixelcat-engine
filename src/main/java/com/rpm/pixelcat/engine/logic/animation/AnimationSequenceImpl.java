package com.rpm.pixelcat.engine.logic.animation;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.clock.GameClock;
import com.rpm.pixelcat.engine.logic.clock.GameClockFactory;
import com.rpm.pixelcat.engine.logic.common.IdGeneratorImpl;
import com.rpm.pixelcat.engine.logic.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class AnimationSequenceImpl extends IdGeneratorImpl implements AnimationSequence {
    List<String> cels;
    Integer currentIndex;
    GameClock animationClock;
    Long millisecondsPerCel;
    Boolean isPaused;
    Boolean isAnimationAccelerationEnabled;
    Long millisecondsForAnimationAcceleration;
    Double animationVelocity;
    Double animationAcceleration;

    public AnimationSequenceImpl(List<String> cels, Long millisecondsPerCel) {
        this();
        addCels(cels);
        setMillisecondsPerCel(millisecondsPerCel);
    }

    public AnimationSequenceImpl(Long millisecondsPerCel) {
        this();
        setMillisecondsPerCel(millisecondsPerCel);
    }

    public AnimationSequenceImpl() {
        super(AnimationSequence.class.toString());
        cels = new ArrayList<>();
        currentIndex = null;
        millisecondsPerCel = 0L;
        isPaused = true;
        isAnimationAccelerationEnabled = true;
        millisecondsForAnimationAcceleration = 0L;
        animationVelocity = 1.0;
        animationAcceleration = 0.0;
        animationClock = GameClockFactory.getInstance().createGameClock();
        animationClock.addEvent("animation cycle start");
    }

    public AnimationSequence addCel(String cel) {
        // validate
        if (cels.isEmpty()) {
            currentIndex = 0;
        }

        // add cel
        cels.add(cel);

        return this;
    }

    public AnimationSequence addCels(List<String> cels) {
        // validate
        if (this.cels.isEmpty()) {
            currentIndex = 0;
        }

        // add cels
        this.cels.addAll(cels);

        return this;
    }

    public AnimationSequence setCurrentCel(String id) throws GameException {
        // validate
        if (!cels.contains(id)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        // update current index
        currentIndex = cels.indexOf(id);

        return this;
    }

    public void setMillisecondsPerCel(Long millisecondsPerCel) {
        this.millisecondsPerCel = millisecondsPerCel;
    }

    public void setAnimationAcceleration(Long timeInMilliseconds) {
        isAnimationAccelerationEnabled = true;
        millisecondsForAnimationAcceleration = timeInMilliseconds;
        animationVelocity = 0.0;
        animationAcceleration = millisecondsPerCel.doubleValue() / timeInMilliseconds;
    }

    public String getCurrentCel() throws GameException {
        if (currentIndex == null) {
            throw new GameException(GameErrorCode.INTERNAL_ERROR);
        }

        return cels.get(currentIndex);
    }

    public void play() {
        isPaused = false;
    }

    public void pause() {
        isPaused = true;
    }

    public void resetSequence() {
        currentIndex = 0;
    }

    public void advanceSequence() {
        if (isPaused) {
            return;
        }

        currentIndex = currentIndex + 1 == cels.size() ? 0 : currentIndex + 1;
    }

    public void advanceSequenceByTime() throws GameException {
        if (isPaused) {
            return;
        }

        if (GameClock.toMS(animationClock.getElapsed("animation cycle start")) * animationVelocity > millisecondsPerCel) {
            animationClock.addEvent("animation cycle start");
            if (isAnimationAccelerationEnabled && animationVelocity < 1.0) {
                animationVelocity += animationAcceleration;
            }

            currentIndex = currentIndex + 1 == cels.size() ? 0 : currentIndex + 1;
        }
    }

    @Override
    public String toString() {
        return "AnimationSequenceImpl{" +
            "cels=" + cels +
            ", currentIndex=" + currentIndex +
            ", animationClock=" + animationClock +
            ", millisecondsPerCel=" + millisecondsPerCel +
            ", isPaused=" + isPaused +
            ", isAnimationAccelerationEnabled=" + isAnimationAccelerationEnabled +
            ", millisecondsForAnimationAcceleration=" + millisecondsForAnimationAcceleration +
            ", animationVelocity=" + animationVelocity +
            ", animationAcceleration=" + animationAcceleration +
            '}';
    }
}
