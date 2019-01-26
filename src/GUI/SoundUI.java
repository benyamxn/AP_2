package GUI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class SoundUI implements Pausable {

    private double rate = 1;

    private Media mainMusic = new Media(getClass().getClassLoader().getResource("Soundtrack/minecraft.mp3").toExternalForm());
    private MediaPlayer mainMusicPlayer = new MediaPlayer(mainMusic);

    private Media splashSound = new Media(getClass().getClassLoader().getResource("Soundtrack/splash.mp3").toExternalForm());
    private MediaPlayer splashSoundPlayer = new MediaPlayer(splashSound);

    private Media waterSound = new Media(getClass().getClassLoader().getResource("Soundtrack/water.mp3").toExternalForm());
    private MediaPlayer waterSoundPlayer = new MediaPlayer(waterSound);

    private Media woodSound = new Media(getClass().getClassLoader().getResource("Soundtrack/sawing_wood.mp3").toExternalForm());
    private MediaPlayer woodSoundPlayer = new MediaPlayer(woodSound);

    private Media hammerSound = new Media(getClass().getClassLoader().getResource("Soundtrack/hammer.mp3").toExternalForm());
    private MediaPlayer hammerSoundPlayer = new MediaPlayer(hammerSound);

    private Media clickSound = new Media(getClass().getClassLoader().getResource("Soundtrack/click.mp3").toExternalForm());
    private MediaPlayer clickSoundPlayer = new MediaPlayer(clickSound);

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
                waterSoundPlayer.setRate(rate * 2);
                waterSoundPlayer.play();
                break;
            case "upgrade":
                woodSoundPlayer = new MediaPlayer(woodSound);
                woodSoundPlayer.setStopTime(new Duration(3000));
                woodSoundPlayer.setRate(rate * 1.5);
                woodSoundPlayer.setVolume(0.1);
                woodSoundPlayer.play();

                hammerSoundPlayer = new MediaPlayer(hammerSound);
                hammerSoundPlayer.setStopTime(new Duration(3000));
                hammerSoundPlayer.setRate(rate * 1.5);
                hammerSoundPlayer.setVolume(100);
                hammerSoundPlayer.play();
                break;
            case "click":
                clickSoundPlayer = new MediaPlayer(clickSound);
                clickSoundPlayer.play();
                break;
        }
    }

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public void pause() {
        hammerSoundPlayer.pause();
        woodSoundPlayer.pause();
        waterSoundPlayer.pause();
        splashSoundPlayer.pause();
    }

    @Override
    public void resume() {
        hammerSoundPlayer.play();
        woodSoundPlayer.play();
        waterSoundPlayer.play();
        splashSoundPlayer.play();
    }
}
