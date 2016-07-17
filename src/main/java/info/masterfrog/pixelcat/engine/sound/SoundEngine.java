package info.masterfrog.pixelcat.engine.sound;

import info.masterfrog.pixelcat.engine.exception.TerminalGameException;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.resource.SoundResource;

import java.util.Map;

public interface SoundEngine {
    public enum SoundResourceState {
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

    void process(Map<SoundResourceState, SoundResource> soundResourceStateMap) throws TransientGameException;
}
