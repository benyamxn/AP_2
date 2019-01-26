package GUI;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import model.Farm;
import model.Game;
import model.Mission;
import model.exception.MoneyNotEnoughException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMenu {
    public void render() {
        double width = MainStage.getInstance().getWidth();
        double height = MainStage.getInstance().getHeight();
        Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 42);
        AnchorPane pane = new AnchorPane();
        pane.setId("mainMenuPane");
        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);
        menuBox.setId("menuBox");
        double anchor = (400 > height / 3)? height / 2 - 200: height / 3;
        pane.setBottomAnchor(menuBox, anchor);
        pane.setTopAnchor(menuBox, anchor);
        pane.setRightAnchor(menuBox, 100.0);
        pane.setLeftAnchor(menuBox, width - 300);
        pane.getChildren().add(menuBox);
        Label titleLabel = new Label("Friendly  Farm");
        titleLabel.setId("titleLabel");
        pane.setTopAnchor(titleLabel, 80.0);
        pane.setLeftAnchor(titleLabel, 100.0);
        pane.getChildren().add(titleLabel);
        createButtons(menuBox);
        MainStage.getInstance().getScene().getStylesheets().add(getClass().
                getResource("CSS/mainMenu.css").toExternalForm());
        MainStage.getInstance().pushStack(pane);
    }

    private void createButtons(VBox vBox) {
        Button newGameButton = new Button("New Game");
        Button loadGameButton = new Button("Load Game");
        Button settingsButton = new Button("Settings");
        Button aboutButton = new Button("About");
        Button exitButton = new Button("Exit");
        exitButton.setOnMouseClicked(event ->System.exit(0));
        newGameButton.setOnMouseClicked(event -> {
            try {
                new FarmGUI(new Controller()).render();
                FarmGUI.getSoundPlayer().playTrack("click");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        loadGameButton.setOnMouseClicked(event -> createLoadGameButton());
        exitButton.setOnMouseClicked(event -> System.exit(0));
        VBox.setMargin(newGameButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(loadGameButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(settingsButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(aboutButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(exitButton, new Insets(10, 20, 10, 20));
        vBox.getChildren().addAll(newGameButton, loadGameButton, settingsButton, aboutButton, exitButton);
        for (Node child : vBox.getChildren()) {
            Hoverable.setMouseHandler(child);
        }

    }

    public static void createLoadGameButton() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Game File");
        File file = fileChooser.showOpenDialog(MainStage.getInstance().getScene().getWindow());
        if (file == null) {
            return;
        }
        FarmGUI.anchorPane = new AnchorPane();
        Controller controller = new Controller();
        try {
            controller.loadGame(file.getPath());
            new FarmGUI(controller).render();
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "No such file").show();
            return;
        }
    }
}
