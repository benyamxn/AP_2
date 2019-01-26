package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import model.Game;
import model.VehicleType;
import model.exception.NotEnoughCapacityException;
import model.exception.VehicleOnTripException;

public class HelicopterMenu {
    private Game game;
    private double totalWidth = MainStage.getInstance().getWidth();
    private double totalHeight = MainStage.getInstance().getHeight();
    private AnchorPane pane = new AnchorPane();
    private HelicopterMenuTable table;
    private FarmGUI farmGUI;

    public HelicopterMenu(Game game ,FarmGUI farmGUI) {
        this.farmGUI = farmGUI;
        pane.getStyleClass().add("vehicleMenu");
        System.out.println(totalWidth);
        this.game = game;
        pane.setId("helicopterMenu");
        table = new HelicopterMenuTable(game, totalWidth * 0.6, totalHeight * 0.7);
        table.relocate(20, 20);
        table.addToRoot(pane);
        createButtons();
        renderStatus();
        MainStage.getInstance().pushStack(pane);
    }
    private void renderStatus() {
        pane.getChildren().add(table.getStatusBox());
        table.getStatusBox().relocate(totalWidth * 0.7, totalHeight * 0.4);
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
                game.clear(VehicleType.HELICOPTER);
                farmGUI.resume();
            } catch (VehicleOnTripException | NotEnoughCapacityException e) {
                e.printStackTrace();
            }
            MainStage.getInstance().popStack();
        });
        Hoverable.setMouseHandler(shipButton);
        shipButton.setOnMouseClicked(event -> {
            MainStage.getInstance().popStack();
            game.getFarm().getHelicopter().startTravel();
            FarmCityView.getInstance().runHelicopter();
        });
        hbox.getChildren().addAll(shipButton, cancelButton);
        AnchorPane.setLeftAnchor(hbox, 0.5 * totalWidth - 180);
        AnchorPane.setBottomAnchor(hbox, 20.0);
        pane.getChildren().add(hbox);
    }
}
