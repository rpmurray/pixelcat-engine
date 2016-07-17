package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.sound.SoundEngine;

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
