package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

public interface LoadableResource {
    Boolean isLoaded();

    void load() throws TransientGameException;
}
