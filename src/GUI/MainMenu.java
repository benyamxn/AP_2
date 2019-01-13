package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainMenu {
    public void render() {
        double width = MainStage.getInstance().getWidth();
        double height = MainStage.getInstance().getHeight();
        AnchorPane pane = new AnchorPane();
        pane.setId("mainMenuPane");
        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);
        menuBox.setId("menuBox");
        pane.setBottomAnchor(menuBox, height / 3);
        pane.setTopAnchor(menuBox, height / 3);
        pane.setRightAnchor(menuBox, 100.0);
        pane.setLeftAnchor(menuBox, width - 300);
        pane.getChildren().add(menuBox);
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
        VBox.setMargin(newGameButton, new Insets(20, 20, 20, 20));
        VBox.setMargin(loadGameButton, new Insets(20, 20, 20, 20));
        VBox.setMargin(settingsButton, new Insets(20, 20, 20, 20));
        VBox.setMargin(aboutButton, new Insets(20, 20, 20, 20));
        VBox.setMargin(exitButton, new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(newGameButton, loadGameButton, settingsButton, aboutButton, exitButton);
    }
}
