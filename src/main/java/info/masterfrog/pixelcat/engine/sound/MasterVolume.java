package info.masterfrog.pixelcat.engine.sound;

import java.util.Set;

public interface MasterVolume extends SoundEngine.SoundEventActor {
    public float getValue();

    public void setValue(float value);

    public Set<SoundEngine.SoundEventState> getSoundEventStates();

    public Boolean containsSoundEventStates();

    public void resetSoundEventStates();

    static MasterVolume create(float volume) {
        return new MasterVolumeImpl(volume);
    }
}
