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
}
