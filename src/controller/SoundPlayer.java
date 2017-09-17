package controller;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SoundPlayer {
    private static SoundPlayer instance = new SoundPlayer();
    private static MediaPlayer mediaPlayer;

    private SoundPlayer(){
        JFXPanel fxPanel = new JFXPanel(); // To initialise it
    }

    public static void playMusic(String soundFilename){
        URL soundFile = null;
        try {
            soundFile = new File(soundFilename).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        final Media media = new Media(soundFile.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void playSound(String soundFilename) {
        AudioClip sound = new AudioClip(new File(soundFilename).toURI().toString());
        sound.play();
    }
}
