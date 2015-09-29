package com.rpm.pixelcat.engine.logic.gameobject.feature;

import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.animation.AnimationSequence;

public interface AnimationSequenceLibrary extends LibraryTemplate<AnimationSequence> {
    static AnimationSequenceLibrary create() throws GameException {
        return Feature.create(AnimationSequenceLibraryImpl.class);
    }
}
