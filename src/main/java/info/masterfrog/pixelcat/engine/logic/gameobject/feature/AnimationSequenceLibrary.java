package info.masterfrog.pixelcat.engine.logic.gameobject.feature;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.animation.AnimationSequence;

public interface AnimationSequenceLibrary extends LibraryTemplate<AnimationSequence> {
    static AnimationSequenceLibrary create() throws TransientGameException {
        return create(AnimationSequenceLibraryImpl.class);
    }
}
