package com.rpm.pixelcat.engine.logic.resource;

import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.sound.SoundEngine;

class SoundResourceImpl extends ResourceImpl implements SoundResource {
    private String fileName;
    private Boolean isLoaded;
    private SoundEngine.SoundResourceState state;
    private Boolean stageChanged;

    SoundResourceImpl(String fileName) {
        this.fileName = fileName;
        this.isLoaded = false;
        this.state = SoundEngine.SoundResourceState.STOP;
        this.stageChanged = false;
    }

    public Boolean isLoaded() {
        return isLoaded;
    }

    public void load() throws TransientGameException {
        if (!isLoaded) {
            SoundEngine.getInstance().loadStreamingSource(getId(), fileName);
        }
    }

    public void play() {
        state = SoundEngine.SoundResourceState.PLAY;
        stageChanged = true;
    }

    public void pause() {
        state = SoundEngine.SoundResourceState.PAUSE;
        stageChanged = true;
    }

    public void stop() {
        state = SoundEngine.SoundResourceState.STOP;
        stageChanged = true;
    }

    public SoundEngine.SoundResourceState getState() {
        return state;
    }

    public Boolean hasStageChanged() {
        return stageChanged;
    }

    public void resetStateChanged() {
        stageChanged = false;
    }

    @Override
    public String toString() {
        return "SoundResourceImpl{" +
            "fileName='" + fileName + '\'' +
            ", isLoaded=" + isLoaded +
            ", state=" + state +
            ", stageChanged=" + stageChanged +
            '}';
    }
}
