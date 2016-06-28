package com.rpm.pixelcat.engine.sound;

import com.rpm.pixelcat.engine.common.printer.Printer;
import com.rpm.pixelcat.engine.common.printer.PrinterFactory;
import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.TerminalErrorException;
import com.rpm.pixelcat.engine.exception.TerminalGameException;
import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.resource.SoundResource;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOgg;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;

import java.util.Map;

class SoundEngineImpl implements SoundEngine {
    private SoundSystem soundSystem;
    private Boolean isInitialized;

    private static Printer PRINTER = PrinterFactory.getInstance().createPrinter(SoundEngineImpl.class);
    private static SoundEngineImpl instance = null;

    private SoundEngineImpl() {
        isInitialized = false;
    }

    static SoundEngineImpl getInstance() {
        if (instance == null) {
            instance = new SoundEngineImpl();
        }

        return instance;
    }

    public SoundEngineImpl init() throws TerminalGameException {
        // Instantiate the SoundSystem:
        try {
            soundSystem = new SoundSystem(LibraryJavaSound.class);
//            soundSystem = new SoundSystem(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec( "wav", CodecWav.class );
            SoundSystemConfig.setCodec( "ogg", CodecJOgg.class );
        } catch( SoundSystemException e ) {
            throw new TerminalGameException(GameErrorCode.SOUND_ENGINE_ERROR, e);
        }

        isInitialized = true;

        return this;
    }

    public Boolean isInitialized() {
        return isInitialized;
    }

    public void loadStreamingSource(String id, String fileName) {
        soundSystem.newStreamingSource(
            false,
            id,
            fileName,
            false,
            0, 0, 0,
            SoundSystemConfig.ATTENUATION_NONE,
            0
        );
    }

    private void playSound(String id) throws TransientGameException {
        try {
            soundSystem.play(id);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.SOUND_ENGINE_ERROR);
        }
    }

    private void pauseSound(String id) throws TransientGameException {
        try {
            soundSystem.pause(id);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.SOUND_ENGINE_ERROR);
        }
    }

    private void stopSound(String id) throws TransientGameException {
        try {
            soundSystem.stop(id);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.SOUND_ENGINE_ERROR);
        }
    }

    public void process(Map<SoundResourceState, SoundResource> soundResourceStateMap) throws TransientGameException {
        for (SoundResourceState soundResourceState : soundResourceStateMap.keySet()) {
            SoundResource soundResource = soundResourceStateMap.get(soundResourceState);
            if (!soundResource.isLoaded()) {
                soundResource.load();
            }

            if (!soundResource.hasStageChanged()) {
                continue;
            }

            switch (soundResourceState) {
                case PLAY:
                    playSound(soundResource.getId());
                    break;
                case PAUSE:
                    pauseSound(soundResource.getId());
                    break;
                case STOP:
                    stopSound(soundResource.getId());
                    break;
                default:
                    throw new TransientGameException(GameErrorCode.SOUND_ENGINE_ERROR);
            }

            soundResource.resetStateChanged();
        }
    }

}
