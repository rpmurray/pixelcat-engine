package info.masterfrog.pixelcat.engine.logic.gameobject.feature;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.resource.SoundResource;

public interface SoundLibrary extends LibraryTemplate<SoundResource> {
    static SoundLibrary create() throws TransientGameException {
        return Feature.create(SoundLibraryImpl.class);
    }
}
