package ir.nimac.controler.client;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class SoundPlayer {

    private static final int dur = 300000;
    private static final Object lock = new Object();
    private static SoundPlayer soundPlayer = null;
    private static JFXPanel jfxPanel = new JFXPanel();
    private static ArrayList<MediaPlayer> mediaPlayer = new ArrayList<>();

    private SoundPlayer() {

    }

    public static SoundPlayer getInstance() {
        if (soundPlayer == null) {
            soundPlayer = new SoundPlayer();
        }
        return soundPlayer;
    }

    public synchronized void playSound(final String url) {
        Media hit = new Media(new File(url).toURI().toString());
        mediaPlayer.add(new MediaPlayer(hit));
        mediaPlayer.get(mediaPlayer.size() - 1).play();
    }

    public synchronized void playContinuessSound(final String url) {
        final Media hit = new Media(new File(url).toURI().toString());
        Thread songThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.add(new MediaPlayer(hit));
                mediaPlayer.get(mediaPlayer.size() - 1).play();
                final Date lastPlay = new Date();
                while (true) {
                    if (lastPlay.getTime() + dur < (new Date()).getTime()) {
                        mediaPlayer.add(new MediaPlayer(hit));
                        mediaPlayer.get(mediaPlayer.size() - 1).play();
                        lastPlay.setTime(new Date().getTime());
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        songThread.start();
    }
}
