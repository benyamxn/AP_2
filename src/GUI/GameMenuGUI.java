package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
        exitButton = new Button("Exit");
        exitButton.setOnMouseClicked(event -> System.exit(0));
        VBox.setMargin(continueButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(saveButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(loadButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(exitButton, new Insets(10, 20, 10, 20));
        vBox.getChildren().addAll(continueButton,loadButton,saveButton,exitButton);
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

}
