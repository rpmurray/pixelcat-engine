package info.masterfrog.pixelcat.engine.sound;

import java.util.HashSet;
import java.util.Set;

class MasterVolumeImpl implements MasterVolume {
    private float value;
    private Set<SoundEngine.SoundEventState> soundEventStates;

    MasterVolumeImpl(float value) {
        this.value = value;
        soundEventStates = new HashSet<>();
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
        soundEventStates.add(SoundEngine.SoundEventState.SET_VOLUME);
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
        return "MasterVolume{" +
            "value=" + value +
            ", soundEventStates=" + soundEventStates +
            '}';
    }
}
