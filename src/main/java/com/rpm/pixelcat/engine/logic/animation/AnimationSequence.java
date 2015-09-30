package com.rpm.pixelcat.engine.logic.animation;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGenerator;
import com.rpm.pixelcat.engine.logic.common.RenderableContainer;
import com.rpm.pixelcat.engine.logic.resource.Resource;

import java.util.List;

public interface AnimationSequence extends RenderableContainer, IdGenerator {
    public AnimationSequence addCel(String cel);

    public AnimationSequence addCels(List<String> cels);

    public AnimationSequence setCurrentCel(String id) throws GameException;

    public void setMillisecondsPerCel(Long millisecondsPerCel);

    public void setAnimationAcceleration(Long timeInMilliseconds);

    public String getCurrentCel() throws GameException;

    public void play();

    public void pause();

    public void resetSequence();

    public void advanceSequence();

    public void advanceSequenceByTime() throws GameException;
}
