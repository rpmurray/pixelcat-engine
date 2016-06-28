package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.sound.SoundEngine;

public interface SoundResource extends Resource {
    Boolean isLoaded();

    void load() throws TransientGameException;

    void play();

    void pause();

    void stop();

    SoundEngine.SoundResourceState getState();

    Boolean hasStageChanged();

    void resetStateChanged();
}
