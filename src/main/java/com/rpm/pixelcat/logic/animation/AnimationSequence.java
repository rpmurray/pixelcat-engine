package com.rpm.pixelcat.logic.animation;

import com.rpm.pixelcat.exception.GameException;
import com.rpm.pixelcat.logic.resource.Resource;

import java.util.List;

public interface AnimationSequence {
    public void addCel(Resource cel);

    public void addCels(List<Resource> cels);

    public void setMillisecondsPerCel(Long millisecondsPerCel);

    public void setAnimationAcceleration(Long timeInMilliseconds);

    public Resource getCurrentCel() throws GameException;

    public void play();

    public void pause();

    public void resetSequence();

    public void advanceSequence();

    public void advanceSequenceByTime(Long currentTimeInMilliseconds);
}