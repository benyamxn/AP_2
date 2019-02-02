package multiplayer.multiplayerGUI;

import GUI.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Game;
import model.ProductType;
import model.VehicleType;
import model.exception.NotEnoughCapacityException;
import model.exception.VehicleOnTripException;
import multiplayer.ClientSenderThread;
import multiplayer.multiplayerModel.messages.TruckContentsMessage;
import multiplayer.multiplayerModel.messages.TruckItemsMessage;

import java.util.Map;

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

    @Override
    protected void createButtons() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        Button shipButton = new Button("Ship");
        shipButton.getStyleClass().add("truckMenuBtn");
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("truckMenuBtn");
        Hoverable.setMouseHandler(cancelButton);
        cancelButton.setOnMouseClicked(event -> {
            try {
                FarmGUI.getSoundPlayer().playTrack("click");
                game.clear(VehicleType.TRUCK);
                farmGUI.resume();

            } catch (VehicleOnTripException | NotEnoughCapacityException e) {
                e.printStackTrace();
            }
            MainStage.getInstance().popStack();
        });
        Hoverable.setMouseHandler(shipButton);
        shipButton.setOnMouseClicked(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
//            FarmGUI.getSoundPlayer().playTrack("truck travel");
            farmGUI.resume();
            game.getFarm().getWarehouse().updateGraphics();
            MainStage.getInstance().popStack();
            FarmCityView.getInstance().runTruck(table.getMoney(), game.getFarm().getTruck().canTravel());
            game.getFarm().getTruck().startTravel();
            TruckContentsMessage truckContentsMessage = new TruckContentsMessage();
            for (Map.Entry<ProductType, Integer> entry : game.getFarm().getTruck().getContents().entrySet()) {
                truckContentsMessage.addToMap(entry.getKey(), entry.getValue());
            }
            ClientSenderThread.getInstance().addToQueue(truckContentsMessage);
        });
        hbox.getChildren().addAll(shipButton, cancelButton);
        AnchorPane.setLeftAnchor(hbox, 0.5 * totalWidth - 180);
        AnchorPane.setBottomAnchor(hbox, 20.0);
        pane.getChildren().add(hbox);
    }
}
