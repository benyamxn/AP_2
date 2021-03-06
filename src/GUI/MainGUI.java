package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainStage.getInstance().setStage(primaryStage);
        new MainMenu().render();
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
