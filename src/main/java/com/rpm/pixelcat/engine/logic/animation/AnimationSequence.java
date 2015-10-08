package com.rpm.pixelcat.engine.logic.animation;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;
import com.rpm.pixelcat.engine.logic.common.RenderableContainer;

import java.util.List;

public interface AnimationSequence extends RenderableContainer, IdGenerator {
    public AnimationSequence addCel(String cel);

    public AnimationSequence addCels(List<String> cels);

    public AnimationSequence setCurrentCel(String id) throws GameException;

    public AnimationSequence setMillisecondsPerCel(Long millisecondsPerCel);

    public AnimationSequence setAnimationAcceleration(Long timeInMilliseconds);

    public String getCurrentCel() throws GameException;

    public AnimationSequence play();

    public AnimationSequence pause();

    public AnimationSequence resetSequence();

    public AnimationSequence advanceSequence();

    public AnimationSequence advanceSequenceByTime() throws GameException;
}
