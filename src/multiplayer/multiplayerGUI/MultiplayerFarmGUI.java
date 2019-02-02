package multiplayer.multiplayerGUI;

import GUI.*;
import controller.Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import multiplayer.ClientSenderThread;
import multiplayer.multiplayerModel.messages.StatusUpdateMessage;

import java.io.FileNotFoundException;

public class MultiplayerFarmGUI extends FarmGUI {

    public MultiplayerFarmGUI(Controller controller) throws FileNotFoundException {
        super(controller);
    }

    @Override
    protected void createTruckGUI() {
        VehicleGUI truckGUI = new VehicleGUI(farm.getTruck(), (int) (MainStage.getInstance().getWidth() / 10));
        truckGUI.setOnClick(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            pauseFarm();
            new TruckMenuMultiplayer(game,this);
        });
        UpgradeButton upgradeButton = createVehicleUpgradeButton(truckGUI);
        truckGUI.relocate(MainStage.getInstance().getWidth() / 5, MainStage.getInstance().getHeight() * 0.85);
        upgradeButton.relocate(MainStage.getInstance().getWidth() / 5 - 60, MainStage.getInstance().getHeight()* 0.90);
        truckGUI.addToRoot(anchorPane);
    }

    @Override
    protected void createHelicopterGUI() {
        VehicleGUI helicopterGUI = new VehicleGUI(farm.getHelicopter(), (int) (MainStage.getInstance().getWidth() / 10));
        helicopterGUI.relocate(MainStage.getInstance().getWidth() * 0.7, MainStage.getInstance().getHeight() * 0.85);
        UpgradeButton upgradeButton = createVehicleUpgradeButton(helicopterGUI);
        helicopterGUI.setOnClick(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            pauseFarm();
            new HelicopterMenuMultiplayer(game,this);

        });
        upgradeButton.relocate(MainStage.getInstance().getWidth() * 0.7 - 60, MainStage.getInstance().getHeight()* 0.90);
        helicopterGUI.addToRoot(anchorPane);
    }

    @Override
    protected void createGameUpdater() {
        DurationManager durationManager = new DurationManager(this);
        gameUpdater = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            game.updateGame();
            missionGUI.updateMoney();
            missionGUI.updateProducts();
            ClientSenderThread.getInstance().addToQueue(new StatusUpdateMessage(game.getMoney()));
            if(game.isFinished()){
                createPausePage();
                System.out.println("level finished");
                pauseFarm();
                anchorPane.getChildren().add(pauseRectangle);
                gameMenuGUI.showExit();
            }
        }));
        gameUpdater.setRate(durationManager.getRate());
        gameUpdater.setCycleCount(Animation.INDEFINITE);
        gameUpdater.play();

        createGameSpeedSlider(durationManager);
    }
}
