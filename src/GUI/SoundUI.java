package GUI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class SoundUI {

    private double rate = 1;
    private final Duration start = new Duration(0);

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

    private Media dogSound = new Media(getClass().getClassLoader().getResource("Soundtrack/dog.mp3").toExternalForm());
    private MediaPlayer dogSoundPlayer = new MediaPlayer(dogSound);

    private Media catSound = new Media(getClass().getClassLoader().getResource("Soundtrack/cat.mp3").toExternalForm());
    private MediaPlayer catSoundPlayer = new MediaPlayer(catSound);

    private Media bearSound = new Media(getClass().getClassLoader().getResource("Soundtrack/bear.mp3").toExternalForm());
    private MediaPlayer bearSoundPlayer = new MediaPlayer(bearSound);

    private Media ostrichProduceSound = new Media(getClass().getClassLoader().getResource("Soundtrack/ostrich_produce.mp3").toExternalForm());
    private MediaPlayer ostrichProduceSoundPlayer = new MediaPlayer(ostrichProduceSound);

    private Media ostrichDeathSound = new Media(getClass().getClassLoader().getResource("Soundtrack/ostrich_death.mp3").toExternalForm());
    private MediaPlayer ostrichDeathSoundPlayer = new MediaPlayer(ostrichDeathSound);

    private Media buffaloProduceSound = new Media(getClass().getClassLoader().getResource("Soundtrack/buffalo_produce.mp3").toExternalForm());
    private MediaPlayer buffaloProduceSoundPlayer = new MediaPlayer(buffaloProduceSound);

    private Media buffaloDeathSound = new Media(getClass().getClassLoader().getResource("Soundtrack/buffalo_death.mp3").toExternalForm());
    private MediaPlayer buffaloDeathSoundPlayer = new MediaPlayer(buffaloDeathSound);

    private Media guineaFowlSound = new Media(getClass().getClassLoader().getResource("Soundtrack/guineaFowl.mp3").toExternalForm());
    private MediaPlayer guineaFowlProduceSoundPlayer = new MediaPlayer(guineaFowlSound);

    private MediaPlayer guineaFowlDeathSoundPlayer = new MediaPlayer(guineaFowlSound);

    private Media fightSound = new Media(getClass().getClassLoader().getResource("Soundtrack/fight.mp3").toExternalForm());
    private MediaPlayer fightSoundPlayer = new MediaPlayer(fightSound);
    private double volume = 75;

    SoundUI() {
        initSoundPlayers();
    }

    public void playMainMusic() {
        mainMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mainMusicPlayer.setVolume(0.5);
        mainMusicPlayer.play();
        mainMusicPlayer.setAutoPlay(true);
    }

    private void changeVolumes() {
        splashSoundPlayer.setVolume(volume);
        waterSoundPlayer.setVolume(volume * 0.25);
        woodSoundPlayer.setVolume(volume * 0.1);
        hammerSoundPlayer.setVolume(volume * 100);
        clickSoundPlayer.setVolume(volume);
        bearSoundPlayer.setVolume(volume * 0.5);
        guineaFowlDeathSoundPlayer.setVolume(volume);
        guineaFowlProduceSoundPlayer.setVolume(volume);
        ostrichProduceSoundPlayer.setVolume(volume);
        ostrichDeathSoundPlayer.setVolume(volume * 0.5);
        buffaloProduceSoundPlayer.setVolume(volume);
        buffaloDeathSoundPlayer.setVolume(volume * 0.5);
        catSoundPlayer.setVolume(volume * 0.3);
        dogSoundPlayer.setVolume(volume * 30);
        fightSoundPlayer.setVolume(volume);
    }

    private void initSoundPlayers() {
        splashSoundPlayer = new MediaPlayer(splashSound);

        waterSoundPlayer = new MediaPlayer(waterSound);
        waterSoundPlayer.setVolume(0.25);
        waterSoundPlayer.setRate(rate * 2);

        woodSoundPlayer = new MediaPlayer(woodSound);
        woodSoundPlayer.setStopTime(new Duration(3000));
        woodSoundPlayer.setRate(rate * 1.5);
        woodSoundPlayer.setVolume(0.1);

        hammerSoundPlayer = new MediaPlayer(hammerSound);
        hammerSoundPlayer.setStopTime(new Duration(3000));
        hammerSoundPlayer.setRate(rate * 1.5);
        hammerSoundPlayer.setVolume(100);

        clickSoundPlayer = new MediaPlayer(clickSound);

        guineaFowlProduceSoundPlayer = new MediaPlayer(guineaFowlSound);
        guineaFowlProduceSoundPlayer.setStopTime(new Duration(2500));
        guineaFowlProduceSoundPlayer.setRate(rate * 1.5);

        guineaFowlDeathSoundPlayer = new MediaPlayer(guineaFowlSound);
        guineaFowlDeathSoundPlayer.setStartTime(new Duration(5000));
        guineaFowlDeathSoundPlayer.setStopTime(new Duration(6500));
        guineaFowlDeathSoundPlayer.setVolume(0.5);

        bearSoundPlayer = new MediaPlayer(bearSound);
        bearSoundPlayer.setStartTime(new Duration(1000));
        bearSoundPlayer.setVolume(0.5);

        ostrichProduceSoundPlayer = new MediaPlayer(ostrichProduceSound);
        ostrichProduceSoundPlayer.setRate(rate * 1.5);

        buffaloProduceSoundPlayer = new MediaPlayer(buffaloProduceSound);

        ostrichDeathSoundPlayer = new MediaPlayer(ostrichDeathSound);
        ostrichDeathSoundPlayer.setStopTime(new Duration(2500));
        ostrichDeathSoundPlayer.setRate(rate * 1.5);
        ostrichDeathSoundPlayer.setVolume(0.5);

        buffaloDeathSoundPlayer = new MediaPlayer(buffaloDeathSound);
        buffaloDeathSoundPlayer.setRate(rate * 1.5);
        buffaloDeathSoundPlayer.setVolume(0.5);

        catSoundPlayer = new MediaPlayer(catSound);
        catSoundPlayer.setVolume(0.3);

        dogSoundPlayer = new MediaPlayer(dogSound);
        dogSoundPlayer.setVolume(30);

        fightSoundPlayer = new MediaPlayer(fightSound);
        fightSoundPlayer.setCycleCount(2);
        fightSoundPlayer.setRate(rate * 2);

    }

    private void seekSoundPlayers() {
        Duration start = new Duration(0);

        splashSoundPlayer.seek(start);
        splashSoundPlayer.pause();

        waterSoundPlayer.seek(start);
        waterSoundPlayer.pause();

        woodSoundPlayer.seek(start);
        woodSoundPlayer.pause();

        hammerSoundPlayer.seek(start);
        hammerSoundPlayer.pause();

        clickSoundPlayer.seek(start);
        clickSoundPlayer.pause();

        bearSoundPlayer.seek(start);
        bearSoundPlayer.pause();

        guineaFowlProduceSoundPlayer.seek(start);
        guineaFowlProduceSoundPlayer.pause();

        buffaloDeathSoundPlayer.seek(start);
        buffaloDeathSoundPlayer.pause();

        buffaloProduceSoundPlayer.seek(start);
        buffaloProduceSoundPlayer.pause();

        ostrichDeathSoundPlayer.seek(start);
        ostrichDeathSoundPlayer.pause();

        ostrichProduceSoundPlayer.seek(start);
        ostrichProduceSoundPlayer.pause();

        dogSoundPlayer.seek(start);
        dogSoundPlayer.pause();

        catSoundPlayer.seek(start);
        catSoundPlayer.pause();

    }

    public void playTrack (String trackName) {

        switch (trackName.toLowerCase()) {
            case "splash":
                splashSoundPlayer.seek(start);
                splashSoundPlayer.play();
                break;
            case "water":
                waterSoundPlayer.seek(start);
                waterSoundPlayer.play();
                break;
            case "upgrade":
                woodSoundPlayer.seek(start);
                woodSoundPlayer.play();

                hammerSoundPlayer.seek(start);
                hammerSoundPlayer.play();
                break;
            case "click":
                clickSoundPlayer.seek(start);
                clickSoundPlayer.play();
                break;
            case "grizzly":
                bearSoundPlayer.seek(start);
                bearSoundPlayer.play();
                break;
            case "dog":
                dogSoundPlayer.seek(start);
                dogSoundPlayer.play();
                break;
            case "cat":
                catSoundPlayer.seek(start);
                catSoundPlayer.play();
                break;
            case "fight":
                fightSoundPlayer.seek(start);
                fightSoundPlayer.play();
                break;
        }

        if (trackName.contains("produce")) {
            switch (trackName.replace("_", " ").split(" ")[0].toLowerCase()) {
                case "guinea":
                    guineaFowlProduceSoundPlayer.seek(start);
                    guineaFowlProduceSoundPlayer.play();
                    break;
                case "ostrich":
                    ostrichProduceSoundPlayer.seek(start);
                    ostrichProduceSoundPlayer.play();
                    break;
                case "buffalo":
                    buffaloProduceSoundPlayer.seek(start);
                    buffaloProduceSoundPlayer.play();
                    break;
            }
        }

        if (trackName.contains("death")) {
            switch (trackName.replace("_", " ").split(" ")[0].toLowerCase()) {
                case "guinea":
                    guineaFowlDeathSoundPlayer.seek(start);
                    guineaFowlDeathSoundPlayer.play();
                    break;
                case "ostrich":
                    ostrichDeathSoundPlayer.seek(start);
                    ostrichDeathSoundPlayer.play();
                    break;
                case "buffalo":
                    buffaloDeathSoundPlayer.seek(start);
                    buffaloDeathSoundPlayer.play();
                    break;
            }
        }
    }

    public void setMusicSound(double volume) {
        mainMusicPlayer.setVolume(volume * 0.01);
    }

    public void setVolume(double volumePercentage) {
        volume *= volumePercentage / 100;
        changeVolumes();
    }

    public double getVolume() {
        return volume;
    }

    public double getMusicSound() {
        return mainMusicPlayer.getVolume();
    }
}
