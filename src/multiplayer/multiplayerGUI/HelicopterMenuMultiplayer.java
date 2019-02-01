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
import multiplayer.multiplayerModel.messages.HelicopterItems;

import java.util.Map;

public class HelicopterMenuMultiplayer extends HelicopterMenu {
    public HelicopterMenuMultiplayer(Game game, FarmGUI farmGUI) {
        super(game, farmGUI);
        farmGUI.getGame().setSuppressItemNotForSaleException(true);
    }

    @Override
    protected void createTable() {
        table = new HelicopterMenuTableMultiplayer(game, totalWidth * 0.6, totalHeight * 0.7);
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
                game.clear(VehicleType.HELICOPTER);
                farmGUI.resume();
            } catch (VehicleOnTripException | NotEnoughCapacityException e) {
                e.printStackTrace();
            }
            MainStage.getInstance().popStack();
        });
        Hoverable.setMouseHandler(shipButton);
        shipButton.setOnMouseClicked(event -> {
            HelicopterItems helicopterItems = new HelicopterItems();
            for (Map.Entry<ProductType, Integer> entry : game.getFarm().getHelicopter().getContents().entrySet()) {
                helicopterItems.addToProducts(entry.getKey(), entry.getValue());
            }
            ClientSenderThread.getInstance().addToQueue(helicopterItems);
            System.out.println("send the products validation");
        });
        hbox.getChildren().addAll(shipButton, cancelButton);
        AnchorPane.setLeftAnchor(hbox, 0.5 * totalWidth - 180);
        AnchorPane.setBottomAnchor(hbox, 20.0);
        pane.getChildren().add(hbox);
    }

    private void runTheHelicopter() {
        FarmGUI.getSoundPlayer().playTrack("click");
//        FarmGUI.getSoundPlayer().playTrack("helicopter travel");
        MainStage.getInstance().popStack();
        farmGUI.resume();
        game.getFarm().getHelicopter().startTravel();
        FarmCityView.getInstance().runHelicopter(game.getFarm().getHelicopter().canTravel());
    }
}
