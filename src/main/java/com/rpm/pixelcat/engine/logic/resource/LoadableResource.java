package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.TransientGameException;

public interface LoadableResource {
    Boolean isLoaded();

    void load() throws TransientGameException;
}
