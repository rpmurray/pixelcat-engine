package info.masterfrog.pixelcat.engine.logic.resource;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.sound.SoundEngine;

import java.util.HashSet;
import java.util.Set;

class SoundResourceImpl extends ResourceImpl implements SoundResource {
    private String fileName;
    private float volume;
    private Boolean isLoaded;
    private Set<SoundEngine.SoundEventState> soundEventStates;

    SoundResourceImpl(String fileName) {
        this(fileName, 100.0f);
    }

    SoundResourceImpl(String fileName, float volume) {
        this.fileName = fileName;
        this.volume = volume;
        this.isLoaded = false;
        this.soundEventStates = new HashSet<>();
    }

    public Boolean isLoaded() {
        return isLoaded;
    }

    public void load() throws TransientGameException {
        if (!isLoaded) {
            SoundEngine.getInstance().loadStreamingSource(getId(), fileName);
        }
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        soundEventStates.add(SoundEngine.SoundEventState.SET_VOLUME);
    }

    public void play() {
        soundEventStates.add(SoundEngine.SoundEventState.PLAY);
    }

    public void pause() {
        soundEventStates.add(SoundEngine.SoundEventState.PAUSE);
    }

    public void stop() {
        soundEventStates.add(SoundEngine.SoundEventState.STOP);
    }

    public Set<SoundEngine.SoundEventState> getSoundEventStates() {
        return soundEventStates;
    }

    public Boolean containsSoundEventStates() {
        return !soundEventStates.isEmpty();
    }

    public void resetSoundEventStates() {
        soundEventStates.clear();
    }

    @Override
    public String toString() {
        return "SoundResourceImpl{" +
            "fileName='" + fileName + '\'' +
            ", volume=" + volume +
            ", isLoaded=" + isLoaded +
            ", soundEventStates=" + soundEventStates +
            '}';
    }
}
