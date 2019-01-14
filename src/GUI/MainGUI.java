package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainStage.getInstance().setStage(primaryStage);
        new MainMenu().render();
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
