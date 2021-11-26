package Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    public AudioInputStream audioInputStream;
    public Clip clip;

    public Sound(String path) throws UnsupportedAudioFileException, LineUnavailableException {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (IOException e) {
            System.out.println("Error read image");
        }

    }
    public void playSound(boolean loop) throws IOException, LineUnavailableException{
        clip.setFramePosition(0);
        clip.start();
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void endSound() {
        clip.close();
    }

    private static final String path = System.getProperty("user.dir");

    public static Sound mapSound;
    static {
        try {
            mapSound = new Sound(path + "\\Data\\Sound\\playgame.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static Sound newBombSound;
    static {
        try {
            newBombSound = new Sound(path + "\\Data\\Sound\\newbomb.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public static Sound explodedBombSound;
    static {
        try {
            explodedBombSound = new Sound(path + "\\Data\\Sound\\bomb_bang.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public static Sound itemSound;
    static {
        try {
            itemSound = new Sound(path + "\\Data\\Sound\\item.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static Sound footSound;
    static {
        try {
            footSound = new Sound(path + "\\Data\\Sound\\foot.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public static Sound mobDiedSound;
    static {
        try {
            mobDiedSound = new Sound(path + "\\Data\\Sound\\monster_die.wav");
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
