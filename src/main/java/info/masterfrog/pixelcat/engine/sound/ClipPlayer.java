package info.masterfrog.pixelcat.engine.sound;

import info.masterfrog.pixelcat.engine.constants.ResourceType;

import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ClipPlayer {
    private static ClipPlayer instance = null;

    private ClipPlayer() {
        // do nothing
    }

    public static ClipPlayer getInstance() {
        if (instance == null) {
            instance = new ClipPlayer();
        }

        return instance;
    }

    public void playClip(String fileName) throws Exception {
        String filePath = ResourceType.SOUND.getResourceFilePath() + "/" + fileName;
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePath);
        Clip clip = AudioSystem.getClip();
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        clip.open(audioInputStream);
        //clip.loop(Clip.LOOP_CONTINUOUSLY);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // A GUI element to prevent the Clip's daemon Thread
                // from terminating at the end of the main()
                JOptionPane.showMessageDialog(null, "Close to exit!");
            }
        });
    }
}
