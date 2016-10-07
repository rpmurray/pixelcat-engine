package info.masterfrog.pixelcat.engine.sound;

import com.google.common.collect.ImmutableSet;
import info.masterfrog.pixelcat.engine.exception.TerminalGameException;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.util.HashSet;
import java.util.Set;

public interface SoundEngine {
    interface SoundEventActor {
        Set<SoundEventState> getSoundEventStates();

        Boolean containsSoundEventStates();

        void resetSoundEventStates();
    }

    enum SoundEventState {
        SET_VOLUME,
        PLAY,
        PAUSE,
        STOP,
    }

    static SoundEngine getInstance() {
        return SoundEngineImpl.getInstance();
    }

    SoundEngine init() throws TerminalGameException;

    Boolean isInitialized();

    void loadStreamingSource(String id, String fileName);

    void process(Set<SoundEventActor> soundEventActors) throws TransientGameException;
}
