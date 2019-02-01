package multiplayer.multiplayerGUI;

import GUI.FarmGUI;
import GUI.MainStage;
import GUI.TruckMenu;
import GUI.TruckMenuTable;
import model.Game;

public class TruckMenuMultiplayer extends TruckMenu {
    public TruckMenuMultiplayer(Game game, FarmGUI farmGUI) {
        super(game, farmGUI);
    }

    @Override
    protected void createTable() {
        table = new TruckMenuTableMultiplayer(game, totalWidth * 0.4, totalHeight * 0.7);
        table.relocate(20, 20);
        table.addToRoot(pane);
    }
}
