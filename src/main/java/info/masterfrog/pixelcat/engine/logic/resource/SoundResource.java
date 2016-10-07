package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.sound.SoundEngine;

public interface SoundResource extends Resource, SoundEngine.SoundEventActor {
    Boolean isLoaded();

    void load() throws TransientGameException;

    float getVolume();

    void setVolume(float volume);

    void play();

    void pause();

    void stop();
}
