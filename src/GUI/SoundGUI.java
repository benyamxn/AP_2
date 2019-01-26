package GUI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class SoundGUI {

    private Media mainMusic = new Media(getClass().getClassLoader().getResource("Soundtrack/minecraft.mp3").toExternalForm());
    private MediaPlayer mainMusicPlayer = new MediaPlayer(mainMusic);

    private Media splashSound = new Media(getClass().getClassLoader().getResource("Soundtrack/splash.mp3").toExternalForm());
    private MediaPlayer splashSoundPlayer;

    private Media waterSound = new Media(getClass().getClassLoader().getResource("Soundtrack/water.mp3").toExternalForm());
    private MediaPlayer waterSoundPlayer;

    private Media woodSound = new Media(getClass().getClassLoader().getResource("Soundtrack/sawing_wood.mp3").toExternalForm());
    private MediaPlayer woodSoundPlayer;

    private Media hammerSound = new Media(getClass().getClassLoader().getResource("Soundtrack/hammer.mp3").toExternalForm());
    private MediaPlayer hammerSoundPlayer;

    private Media clickSound = new Media(getClass().getClassLoader().getResource("Soundtrack/click.mp3").toExternalForm());
    private MediaPlayer clickSoundPlayer;

    public void playMainMusic() {
        mainMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mainMusicPlayer.play();
        mainMusicPlayer.setAutoPlay(true);
    }

    public void playTrack (String trackName) {
        switch (trackName.toLowerCase()) {
            case "splash":
                splashSoundPlayer = new MediaPlayer(splashSound);
                splashSoundPlayer.play();
                break;
            case "water":
                waterSoundPlayer = new MediaPlayer(waterSound);
                waterSoundPlayer.setVolume(0.25);
                waterSoundPlayer.setRate(2);
                waterSoundPlayer.play();
                break;
            case "upgrade":
                woodSoundPlayer = new MediaPlayer(woodSound);
                woodSoundPlayer.setStopTime(new Duration(3000));
                woodSoundPlayer.setRate(1.5);
                woodSoundPlayer.setVolume(0.1);
                woodSoundPlayer.play();

                hammerSoundPlayer = new MediaPlayer(hammerSound);
                hammerSoundPlayer.setStopTime(new Duration(3000));
                hammerSoundPlayer.setRate(1.5);
                hammerSoundPlayer.setVolume(100);
                hammerSoundPlayer.play();
                break;
            case "click":
                clickSoundPlayer = new MediaPlayer(clickSound);
                clickSoundPlayer.play();
                break;
        }
    }

}
