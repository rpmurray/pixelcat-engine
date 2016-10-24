package info.masterfrog.pixelcat.engine.sound;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import info.masterfrog.pixelcat.engine.common.printer.PrinterFactory;
import info.masterfrog.pixelcat.engine.constants.ResourceType;
import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioPlayer implements Runnable {
    private static AudioPlayer instance = null;

    private String fileName = null;

    private volatile boolean stopFlag = false;
    private volatile boolean pauseFlag = false;
    private volatile boolean isPlayingFlag = false;
    private volatile float volume_dB = 0.0f;

    private static Printer PRINTER = PrinterFactory.getInstance().createPrinter(AudioPlayer.class);

    private AudioPlayer() {
        // do nothing
    }

    public static AudioPlayer getInstance() {
        if (instance == null) {
            instance = new AudioPlayer();
        }

        return instance;
    }

    /**
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws FileNotFoundException
     * @throws LineUnavailableException
     */
    private void playSound() throws TransientGameException, FileNotFoundException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        isPlayingFlag = true;

        if (fileName == null) {
            throw new TransientGameException(GameEngineErrorCode.INTERNAL_ERROR);
        }

        if (fileName.toLowerCase().endsWith(".txt")) {
            UnsupportedAudioFileException e = new UnsupportedAudioFileException("Text Files Not Supported!");
            PRINTER.printError(e);
            throw e;
        } else //if (fileName.toLowerCase().endsWith(".mp3") || fileName.toLowerCase().endsWith(".ogg"))
        {
            String filePath = ResourceType.SOUND.getResourceFilePath() + "/" + fileName;
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePath);
            final AudioInputStream in = AudioSystem.getAudioInputStream(inputStream);

            final AudioFormat baseFormat = in.getFormat();
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);
            PRINTER.printDebug("Channels : " + baseFormat.getChannels());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFormat, in);
            final byte[] data = new byte[4096];
            try {
                SourceDataLine res = null;
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                res = (SourceDataLine) AudioSystem.getLine(info);
                res.open(audioFormat);
                SourceDataLine sourceLine = res;
                PRINTER.printDebug("Sound playing started...");

                // Start
                onPlay();
                sourceLine.start();
                int nBytesRead = 0, nBytesWritten = 0;
                while ((nBytesRead != -1) && (!stopFlag)) {
                    if (!pauseFlag) {
                        isPlayingFlag = true;
                        nBytesRead = audioStream.read(data, 0, data.length);
                        if (nBytesRead != -1) {
                            if (sourceLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                                ((FloatControl) sourceLine.getControl(FloatControl.Type.MASTER_GAIN)).setValue(volume_dB);
                            }
                            nBytesWritten = sourceLine.write(data, 0, nBytesRead);
                            PRINTER.printDebug("... -->" + data[0] + " bytesWritten:" + nBytesWritten);
                        }
                    } else {
                        isPlayingFlag = false;
                    }
                }
                PRINTER.printDebug("Sound playing complete...");

                // Stop
                sourceLine.drain();
                sourceLine.stop();
                sourceLine.close();
                audioStream.close();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            in.close();

            isPlayingFlag = false;
            onStop();

        }
    }

    // control methods

    public void play() {
        run();
    }

    public void pause() {
        pauseFlag = true;
        onPause();
    }

    public void resume() {
        pauseFlag = false;
        onResume();
    }

    public void stop() {
        stopFlag = true;
    }

    public boolean isPlaying() {
        return isPlayingFlag;
    }

    public boolean isPaused() {
        return pauseFlag;
    }

    // setup methods

    public AudioPlayer setFile(String filename) {
        this.fileName = filename;

        return this;
    }

    public AudioPlayer setVolume(Float volume) {
        volume = volume == null ? 1.0F : volume;
        volume = volume <= 0.0F ? 0.0001F : volume;
        setVolumeInDecibels((float) (20.0 * (Math.log(volume) / Math.log(10.0))));

        return this;
    }

    public AudioPlayer setVolumeInDecibels(Float decibels) {
        decibels = decibels == null ? 0.0F : decibels;
        this.volume_dB = decibels;

        return this;
    }

    // event handlers

    @Override
    public void run() {
        stopFlag = false;
        pauseFlag = false;

        try {
            playSound();
        } catch (Exception e) {
            PRINTER.printError(e);
        }
    }

    public void onPlay() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onStop() {
    }
}
