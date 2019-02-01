package multiplayer.multiplayerGUI;

import GUI.*;
import controller.Controller;

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
}
