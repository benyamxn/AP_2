package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import model.Game;
import model.VehicleType;
import model.exception.NotEnoughCapacityException;
import model.exception.VehicleOnTripException;

public class HelicopterMenu {
    protected Game game;
    protected double totalWidth = MainStage.getInstance().getWidth();
    protected double totalHeight = MainStage.getInstance().getHeight();
    protected AnchorPane pane = new AnchorPane();
    protected HelicopterMenuTable table;
    protected FarmGUI farmGUI;

    public HelicopterMenu(Game game ,FarmGUI farmGUI) {
        this.farmGUI = farmGUI;
        this.game = game;
        pane.getStyleClass().add("vehicleMenu");
        System.out.println(totalWidth);
        pane.setId("helicopterMenu");
        createTable();
        createButtons();
        renderStatus();
        MainStage.getInstance().pushStack(pane);
    }

    protected void createTable() {
        table = new HelicopterMenuTable(game, totalWidth * 0.6, totalHeight * 0.7);
        table.relocate(20, 20);
        table.addToRoot(pane);
    }

    protected void renderStatus() {
        pane.getChildren().add(table.getStatusBox());
        table.getStatusBox().relocate(totalWidth * 0.7, totalHeight * 0.4);
    }

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
            FarmGUI.getSoundPlayer().playTrack("click");
//            FarmGUI.getSoundPlayer().playTrack("helicopter travel");
            MainStage.getInstance().popStack();
            farmGUI.resume();
            FarmCityView.getInstance().runHelicopter(game.getFarm().getHelicopter().canTravel());
            game.getFarm().getHelicopter().startTravel();
        });
        hbox.getChildren().addAll(shipButton, cancelButton);
        AnchorPane.setLeftAnchor(hbox, 0.5 * totalWidth - 180);
        AnchorPane.setBottomAnchor(hbox, 20.0);
        pane.getChildren().add(hbox);
    }
}
