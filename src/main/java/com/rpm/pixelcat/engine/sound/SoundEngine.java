package com.rpm.pixelcat.engine.sound;

import com.rpm.pixelcat.engine.exception.TerminalGameException;
import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.resource.SoundResource;

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
