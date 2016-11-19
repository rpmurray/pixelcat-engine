package info.masterfrog.pixelcat.engine.logic.gameobject.element.feature;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.animation.AnimationSequence;
import info.masterfrog.pixelcat.engine.logic.gameobject.util.library.LibraryTemplate;

public interface AnimationSequenceLibrary extends LibraryTemplate<AnimationSequence> {
    static AnimationSequenceLibrary create() throws TransientGameException {
        return Feature.create(AnimationSequenceLibraryImpl.class);
    }
}
