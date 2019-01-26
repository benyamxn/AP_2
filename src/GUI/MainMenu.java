package GUI;

import controller.Controller;
import controller.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
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
    private double width = MainStage.getInstance().getWidth();
    private double height = MainStage.getInstance().getHeight();

    public void render() {
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

        VBox settingsMenu = new VBox();
        settingsButton.setOnMouseClicked(event -> {
            createSettingsMenu();
        });
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

    private void createSettingsMenu() {

        AnchorPane pane = new AnchorPane();
        pane.setId("settingsMenuPane");
        Slider musicSoundSlider = new Slider(0, 100, 75);
        musicSoundSlider.valueProperty().addListener(new ChangeListener<> () {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                MainStage.getInstance().getSoundUI().setMusicSound(newValue.doubleValue());
            }
        });
        musicSoundSlider.relocate(2 * width / 3, height / 2);
        musicSoundSlider.setScaleX(1.5);
        musicSoundSlider.setScaleY(1.5);

        pane.getChildren().addAll(musicSoundSlider);
        MainStage.getInstance().pushStack(pane);
    }

    private void createLoadGameButton() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Game File");
        File file = fileChooser.showOpenDialog(MainStage.getInstance().getScene().getWindow());
        if (file == null) {
            return;
        }
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
