package GUI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class SoundUI {

    private double rate = 1;
    private double volume = 1;
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

    private Media hoverSound = new Media(getClass().getClassLoader().getResource("Soundtrack/hover.mp3").toExternalForm());
    private MediaPlayer hoverOutSoundPlayer = new MediaPlayer(hoverSound);

    private MediaPlayer hoverInSoundPlayer = new MediaPlayer(hoverSound);

    private Media errorSound = new Media(getClass().getClassLoader().getResource("Soundtrack/error.mp3").toExternalForm());
    private MediaPlayer errorSoundPlayer = new MediaPlayer(errorSound);

    private Media helicopterTravelSound = new Media(getClass().getClassLoader().getResource("Soundtrack/helicopter_travel.mp3").toExternalForm());
    private MediaPlayer helicopterTravelSoundPlayer = new MediaPlayer(helicopterTravelSound);

    private Media helicopterUpgradeSound = new Media(getClass().getClassLoader().getResource("Soundtrack/helicopter_upgrade.mp3").toExternalForm());
    private MediaPlayer helicopterUpgradeSoundPlayer = new MediaPlayer(helicopterUpgradeSound);

    private Media truckTravelSound = new Media(getClass().getClassLoader().getResource("Soundtrack/truck_travel.mp3").toExternalForm());
    private MediaPlayer truckTravelSoundPlayer = new MediaPlayer(truckTravelSound);

    private Media truckUpgradeSound = new Media(getClass().getClassLoader().getResource("Soundtrack/truck_upgrade.mp3").toExternalForm());
    private MediaPlayer truckUpgradeSoundPlayer = new MediaPlayer(truckUpgradeSound);

    SoundUI() {
        initSoundPlayers();
    }

    public void playMainMusic() {
        mainMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mainMusicPlayer.setVolume(0.75);
        mainMusicPlayer.play();
        mainMusicPlayer.setAutoPlay(true);
    }

    private void changeVolumes() {
        splashSoundPlayer.setVolume(volume);
        waterSoundPlayer.setVolume(volume * 0.25);
        woodSoundPlayer.setVolume(volume * 0.1);
        hammerSoundPlayer.setVolume(volume);
        clickSoundPlayer.setVolume(volume * 0.2);
        hoverInSoundPlayer.setVolume(volume);
        hoverOutSoundPlayer.setVolume(volume);
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
        errorSoundPlayer.setVolume(volume * 0.5);
        truckTravelSoundPlayer.setVolume(volume * 0.5);
        truckUpgradeSoundPlayer.setVolume(volume * 0.5);
        helicopterTravelSoundPlayer.setVolume(volume * 0.5);
        helicopterUpgradeSoundPlayer.setVolume(volume * 0.3);
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
        hammerSoundPlayer.setVolume(1);

        clickSoundPlayer = new MediaPlayer(clickSound);
        clickSoundPlayer.setVolume(0.2);

        hoverInSoundPlayer = hoverOutSoundPlayer = new MediaPlayer(hoverSound);

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

        errorSoundPlayer = new MediaPlayer(errorSound);
        errorSoundPlayer.setVolume(0.5);

        truckTravelSoundPlayer = new MediaPlayer(truckTravelSound);
        truckTravelSoundPlayer.setRate(rate * 1.6);
        truckTravelSoundPlayer.setVolume(0.5);

        truckUpgradeSoundPlayer = new MediaPlayer(truckUpgradeSound);
        truckUpgradeSoundPlayer.setRate(rate * 2);
        truckUpgradeSoundPlayer.setVolume(0.5);

        helicopterTravelSoundPlayer = new MediaPlayer(helicopterTravelSound);
        helicopterTravelSoundPlayer.setRate(rate * 0.8);
        helicopterTravelSoundPlayer.setVolume(0.5);

        helicopterUpgradeSoundPlayer = new MediaPlayer(helicopterUpgradeSound);
        helicopterUpgradeSoundPlayer.setStartTime(new Duration(5000));
        helicopterUpgradeSoundPlayer.setStopTime(new Duration(8600));
        helicopterUpgradeSoundPlayer.setRate(rate * 1.5);
        helicopterUpgradeSoundPlayer.setVolume(0.3);
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
            case "hover in":
                hoverInSoundPlayer.seek(start);
                hoverInSoundPlayer.play();
                break;
            case "hover out":
                hoverOutSoundPlayer.seek(start);
                hoverOutSoundPlayer.play();
                break;
            case "error":
                errorSoundPlayer.seek(start);
                errorSoundPlayer.play();
                break;
            case "truck upgrade":
                truckUpgradeSoundPlayer.seek(start);
                truckUpgradeSoundPlayer.play();
                break;
            case "helicopter upgrade":
                helicopterUpgradeSoundPlayer.seek(start);
                helicopterUpgradeSoundPlayer.play();
                break;
            case "truck travel":
                truckTravelSoundPlayer.seek(start);
                truckTravelSoundPlayer.play();
                break;
            case "helicopter travel":
                helicopterTravelSoundPlayer.seek(start);
                helicopterTravelSoundPlayer.play();
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
        volume = volumePercentage / 100;
        changeVolumes();
    }

    public void setRate(double rate) {
        this.rate = rate;
        initSoundPlayers();
    }

    public double getVolume() {
        return volume;
    }

    public double getMusicSound() {
        return mainMusicPlayer.getVolume();
    }
}
