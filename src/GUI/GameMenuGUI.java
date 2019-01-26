package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameMenuGUI {

    private AnchorPane pane = FarmGUI.anchorPane;
    private FarmGUI farmGUI;
    private Button continueButton;
    private Button saveButton;
    private Button loadButton;
    private Button exitButton;
    private Button settingsButton;
    VBox menuBox = new VBox();
    public GameMenuGUI(FarmGUI farmGUI) {
        this.farmGUI = farmGUI;
        createButtonMenu();
    }


    public void createButtonMenu(){
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);
        menuBox.setId("menuBox");
        double width = MainStage.getInstance().getWidth() ;
        double height = MainStage.getInstance().getHeight() ;
        double anchor = (400 > height / 3)? height / 2 - 200: height / 3;
        pane.setBottomAnchor(menuBox, anchor);
        pane.setTopAnchor(menuBox, anchor);
        pane.setRightAnchor(menuBox, width / 2 - 100);
        pane.setLeftAnchor(menuBox, width / 2  - 100);
        createButtons(menuBox);
    }

    private void createButtons(VBox vBox) {
        continueButton = new Button("Continue");
        saveButton = new Button("Save");
        loadButton = new Button("Load");
        settingsButton = new Button("Settings");
        exitButton = new Button("Exit");
        VBox.setMargin(continueButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(saveButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(loadButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(settingsButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(exitButton, new Insets(10, 20, 10, 20));
        vBox.getChildren().addAll(continueButton,loadButton,saveButton,settingsButton,exitButton);
        for (Node child : vBox.getChildren()) {
            Hoverable.setMouseHandler(child);
        }
    }

    public void show(){
        FarmGUI.anchorPane.getChildren().add(menuBox);
        render();
    }

    public void render(){
        continueButton.setOnMouseClicked(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            FarmGUI.anchorPane.getChildren().remove(menuBox);
            FarmGUI.anchorPane.getChildren().remove(farmGUI.getPauseRectangle());
            farmGUI.resume();
        });
        saveButton.setOnMouseClicked(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            showFilePathDialog(farmGUI);
        });
        loadButton.setOnMouseClicked(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            MainMenu.createLoadGameButton();
        });
        settingsButton.setOnMouseClicked(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            createSettingsMenu();
        });
        exitButton.setOnMouseClicked(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            FarmGUI.anchorPane.getChildren().remove(menuBox);
            FarmGUI.anchorPane.getChildren().remove(farmGUI.getPauseRectangle());
            MainStage.getInstance().popStack();
            FarmGUI.anchorPane = new AnchorPane();
        });
    }

    private static void showFilePathDialog(FarmGUI farmGUI) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Choose game file");
        File file = fileChooser.showSaveDialog(MainStage.getInstance().getScene().getWindow());

        if (file != null) {
            try {
                farmGUI.getController().saveGame(file.getPath().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createSettingsMenu(){
        menuBox.getChildren().clear();

        Slider musicSoundSlider = new Slider(0, 100, MainStage.getInstance().getSoundUI().getMusicSound()  * 100);
        musicSoundSlider.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                MainStage.getInstance().getSoundUI().setMusicSound(newValue.doubleValue());
            }
        });

        Slider soundEffect = new Slider(0,100,MainStage.getInstance().getSoundUI().getVolume() * 100);
        soundEffect.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                MainStage.getInstance().getSoundUI().setVolume(newValue.doubleValue());
            }
        });
        Text text = new Text("Music Volume:");
        text.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        Text text1 = new Text("Sound Effects:");
        text.setFill(Color.GOLD);
        text1.setFill(Color.GOLD);
        text1.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        Button backButton = new Button("Back");

        VBox.setMargin(text, new Insets(10, 20, 10, 20));
        VBox.setMargin(musicSoundSlider, new Insets(5, 20, 10, 20));
        VBox.setMargin(text1, new Insets(30, 20, 10, 20));
        VBox.setMargin(soundEffect, new Insets(5, 20, 10, 20));
        VBox.setMargin(backButton, new Insets(100, 20, 10, 20));

        menuBox.getChildren().addAll(text,musicSoundSlider,text1,soundEffect,backButton);
        Hoverable.setMouseHandler(backButton);
        backButton.setOnMouseClicked(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            menuBox.getChildren().clear();
            createButtonMenu();
            render();
        });
    }

    public  void showExit(){

        menuBox.getChildren().clear();
        Text text = new Text("YOU WON");
        text.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 30));
        VBox.setMargin(text, new Insets(10, 20, 10, 20));
        VBox.setMargin(exitButton, new Insets(100, 20, 10, 20));
        menuBox.getChildren().addAll(text,exitButton);
        FarmGUI.anchorPane.getChildren().add(menuBox);
        text.setFill(Color.GOLD);
        exitButton.setOnMouseClicked(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            FarmGUI.anchorPane.getChildren().remove(menuBox);
            FarmGUI.anchorPane.getChildren().remove(farmGUI.getPauseRectangle());
            MainStage.getInstance().popStack();
            FarmGUI.anchorPane = new AnchorPane();
        });
        Hoverable.setMouseHandler(exitButton);
    }


}
