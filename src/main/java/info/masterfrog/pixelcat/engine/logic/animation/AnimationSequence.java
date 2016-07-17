package info.masterfrog.pixelcat.engine.logic.animation;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.common.IdGenerator;
import info.masterfrog.pixelcat.engine.logic.common.RenderableContainer;

import java.util.List;

public interface AnimationSequence extends RenderableContainer, IdGenerator {
    public AnimationSequence addCel(String cel);

    public AnimationSequence addCels(List<String> cels);

    public AnimationSequence setCurrentCel(String id) throws TransientGameException;

    public AnimationSequence setMillisecondsPerCel(Long millisecondsPerCel);

    public AnimationSequence setAnimationAcceleration(Long timeInMilliseconds);

    public String getCurrentCel() throws TransientGameException;

    public AnimationSequence play();

    public AnimationSequence pause();

    public AnimationSequence resetSequence();

    public AnimationSequence advanceSequence();

    public AnimationSequence advanceSequenceByTime() throws TransientGameException;
}
