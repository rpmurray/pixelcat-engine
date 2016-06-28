package com.rpm.pixelcat.engine.logic.animation;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.clock.GameClock;
import com.rpm.pixelcat.engine.logic.clock.GameClockFactory;
import com.rpm.pixelcat.engine.logic.clock.SimpleGameClock;
import com.rpm.pixelcat.engine.logic.common.IdGeneratorImpl;

import java.util.ArrayList;
import java.util.List;

class AnimationSequenceImpl extends IdGeneratorImpl implements AnimationSequence {
    private List<String> cels;
    private Integer currentIndex;
    private SimpleGameClock animationClock;
    private Long millisecondsPerCel;
    private Boolean isPaused;
    private Boolean isAnimationAccelerationEnabled;
    private Long millisecondsForAnimationAcceleration;
    private Double animationVelocity;
    private Double animationAcceleration;

    AnimationSequenceImpl(List<String> cels, Long millisecondsPerCel) {
        this();
        addCels(cels);
        setMillisecondsPerCel(millisecondsPerCel);
    }

    AnimationSequenceImpl(Long millisecondsPerCel) {
        this();
        setMillisecondsPerCel(millisecondsPerCel);
    }

    AnimationSequenceImpl() {
        super(AnimationSequence.class.getSimpleName());
        cels = new ArrayList<>();
        currentIndex = null;
        millisecondsPerCel = 0L;
        isPaused = true;
        isAnimationAccelerationEnabled = true;
        millisecondsForAnimationAcceleration = 0L;
        animationVelocity = 1.0;
        animationAcceleration = 0.0;
        animationClock = GameClockFactory.getInstance().createSimpleGameClock();
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

    public AnimationSequence setCurrentCel(String id) throws TransientGameException {
        // validate
        if (!cels.contains(id)) {
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR);
        }

        // update current index
        currentIndex = cels.indexOf(id);

        return this;
    }

    public AnimationSequence setMillisecondsPerCel(Long millisecondsPerCel) {
        this.millisecondsPerCel = millisecondsPerCel;

        return this;
    }

    public AnimationSequence setAnimationAcceleration(Long timeInMilliseconds) {
        isAnimationAccelerationEnabled = true;
        millisecondsForAnimationAcceleration = timeInMilliseconds;
        animationVelocity = 0.0;
        animationAcceleration = millisecondsPerCel.doubleValue() / timeInMilliseconds;

        return this;
    }

    public String getCurrentCel() throws TransientGameException {
        if (currentIndex == null) {
            throw new TransientGameException(GameErrorCode.INTERNAL_ERROR);
        }

        return cels.get(currentIndex);
    }

    public AnimationSequence play() {
        isPaused = false;

        return this;
    }

    public AnimationSequence pause() {
        isPaused = true;

        return this;
    }

    public AnimationSequence resetSequence() {
        currentIndex = 0;

        return this;
    }

    public AnimationSequence advanceSequence() {
        if (isPaused) {
            return this;
        }

        currentIndex = currentIndex + 1 == cels.size() ? 0 : currentIndex + 1;

        return this;
    }

    public AnimationSequence advanceSequenceByTime() throws TransientGameException {
        if (isPaused) {
            return this;
        }

        if (GameClock.toMS(animationClock.getElapsed()) * animationVelocity > millisecondsPerCel) {
            animationClock.reset();
            if (isAnimationAccelerationEnabled && animationVelocity < 1.0) {
                animationVelocity += animationAcceleration;
            }

            currentIndex = currentIndex + 1 == cels.size() ? 0 : currentIndex + 1;
        }

        return this;
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
