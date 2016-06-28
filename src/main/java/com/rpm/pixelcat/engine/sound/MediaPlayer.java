package com.rpm.pixelcat.engine.sound;

import com.rpm.pixelcat.engine.constants.ResourceType;
import javafx.scene.media.Media;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.InputStream;
import java.net.URL;

public class MediaPlayer {
    private static MediaPlayer instance = null;

    private MediaPlayer() {
        // do nothing
    }

    public static MediaPlayer getInstance() {
        if (instance == null) {
            instance = new MediaPlayer();
        }

        return instance;
    }

    public void playMedia(String fileName) throws Exception {
        String filePath = ResourceType.SOUND.getResourceFilePath() + "/" + fileName;
        URL fileUrl = ClassLoader.getSystemResource(filePath);
        Media media = new Media(fileUrl.toString());
        javafx.scene.media.MediaPlayer mediaPlayer = new javafx.scene.media.MediaPlayer(media);
        mediaPlayer.play();
    }
}
