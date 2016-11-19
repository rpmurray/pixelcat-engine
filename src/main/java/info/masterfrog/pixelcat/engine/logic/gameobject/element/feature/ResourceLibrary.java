package info.masterfrog.pixelcat.engine.logic.gameobject.element.feature;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.gameobject.util.library.LibraryTemplate;
import info.masterfrog.pixelcat.engine.logic.resource.Resource;

public interface ResourceLibrary extends LibraryTemplate<Resource> {
    static ResourceLibrary create() throws TransientGameException {
        return Feature.create(ResourceLibraryImpl.class);
    }
}

