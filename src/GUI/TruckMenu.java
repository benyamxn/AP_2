package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import model.Farm;
import model.Game;
import model.VehicleType;
import model.exception.NotEnoughCapacityException;
import model.exception.VehicleOnTripException;

public class TruckMenu {
    private Game game;
    private double totalWidth = MainStage.getInstance().getWidth();
    private double totalHeight = MainStage.getInstance().getHeight();
    private AnchorPane pane = new AnchorPane();
    private TruckMenuTable table;
    private FarmGUI farmGUI;

    public TruckMenu(Game game ,FarmGUI farmGUI) {
        this.farmGUI = farmGUI;
        pane.getStyleClass().add("vehicleMenu");
        System.out.println(totalWidth);
        this.game = game;
        pane.setId("truckMenu");
        table = new TruckMenuTable(game, totalWidth * 0.4, totalHeight * 0.7);
        table.relocate(20, 20);
        table.addToRoot(pane);
        createButtons();
        renderStatus();
        MainStage.getInstance().pushStack(pane);
    }
    private void renderStatus() {
        pane.getChildren().add(table.getStatusBox());
        table.getStatusBox().relocate(totalWidth * 0.6, totalHeight * 0.4);
    }

    private void createButtons() {
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
            FarmGUI.getSoundPlayer().playTrack("truck travel");
            farmGUI.resume();
            game.getFarm().getWarehouse().updateGraphics();
            MainStage.getInstance().popStack();
            game.getFarm().getTruck().startTravel();
            FarmCityView.getInstance().runTruck(table.getMoney());
        });
        hbox.getChildren().addAll(shipButton, cancelButton);
        AnchorPane.setLeftAnchor(hbox, 0.5 * totalWidth - 180);
        AnchorPane.setBottomAnchor(hbox, 20.0);
        pane.getChildren().add(hbox);
    }
}
