package info.masterfrog.pixelcat.engine.sound;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.exception.GameErrorCode;
import info.masterfrog.pixelcat.engine.exception.TerminalGameException;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.logic.resource.SoundResource;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOgg;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

import java.util.Set;

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
        // init sound system
        try {
//            soundSystem = new SoundSystem(LibraryJavaSound.class);
            soundSystem = new SoundSystem(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec("wav", CodecWav.class);
            SoundSystemConfig.setCodec("ogg", CodecJOgg.class);
        } catch (SoundSystemException e) {
            throw new TerminalGameException(GameErrorCode.SOUND_ENGINE_ERROR, e);
        }

        isInitialized = true;

        return this;
    }

    public void shutdown() {
        soundSystem.cleanup();
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

    private void setMasterVolume(float volume) throws TransientGameException {
        try {
            PRINTER.printDebug("Setting master volume to " + volume + "...");
            soundSystem.setMasterVolume(volume);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.SOUND_ENGINE_ERROR);
        }
    }

    private void setVolume(String id, float volume) throws TransientGameException {
        try {
            PRINTER.printDebug("Setting volume for " + id + " to" + volume + "...");
            soundSystem.setVolume(id, volume);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.SOUND_ENGINE_ERROR);
        }
    }

    private void playSound(String id) throws TransientGameException {
        try {
            PRINTER.printDebug("Playing " + id + "...");
            soundSystem.play(id);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.SOUND_ENGINE_ERROR);
        }
    }

    private void pauseSound(String id) throws TransientGameException {
        try {
            PRINTER.printDebug("Pausing " + id + "...");
            soundSystem.pause(id);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.SOUND_ENGINE_ERROR);
        }
    }

    private void stopSound(String id) throws TransientGameException {
        try {
            PRINTER.printDebug("Stopping " + id + "...");
            soundSystem.stop(id);
        } catch (Exception e) {
            throw new TransientGameException(GameErrorCode.SOUND_ENGINE_ERROR);
        }
    }

    public void process(Set<SoundEventActor> soundEventActors) throws TransientGameException {
        for (SoundEventActor soundEventActor : soundEventActors) {

            if (soundEventActor instanceof MasterVolume) {
                // setup
                MasterVolume masterVolume = (MasterVolume) soundEventActor;

                // set master volume
                setMasterVolume(masterVolume.getValue());
            } else {
                // setup
                SoundResource soundResource = (SoundResource) soundEventActor;

                // check loaded
                if (!soundResource.isLoaded()) {
                    soundResource.load();
                }

                // check if state has changed
                if (!soundResource.containsSoundEventStates()) {
                    continue;
                }

                // handle change
                Set<SoundEventState> soundSoundEventStates = soundResource.getSoundEventStates();
                for (SoundEventState soundSoundEventState : soundSoundEventStates) {
                    switch (soundSoundEventState) {
                        case SET_VOLUME:
                            setVolume(soundResource.getId(), soundResource.getVolume());
                            break;
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
                }
            }

            soundEventActor.resetSoundEventStates();
        }
    }

}
