package multiplayer.multiplayerGUI;

import GUI.TruckMenuTable;
import model.Game;
import model.ProductType;
import multiplayer.ClientSenderThread;
import multiplayer.client.ClientHandler;
import multiplayer.multiplayerModel.messages.ReceiverStartedMessage;
import multiplayer.multiplayerModel.messages.TruckMenuRequest;

import java.util.EnumMap;
import java.util.Map;

public class TruckMenuTableMultiplayer extends TruckMenuTable {
    public TruckMenuTableMultiplayer(Game game, double width, double height) {
        super(game, width, height);
        ClientHandler.setTruckMenuTableMultiplayer(this);
    }

    @Override
    protected void fillTableObservableList() {
        super.fillTableObservableList();
        ClientSenderThread.getInstance().addToQueue(new TruckMenuRequest());
    }

    public void updatePrices(EnumMap<ProductType, Integer> products) {
        for (Map.Entry<ProductType, Integer> entry : products.entrySet()) {
            entry.getKey().setSaleCost(entry.getValue());
        }
        table.refresh();
    }
}
