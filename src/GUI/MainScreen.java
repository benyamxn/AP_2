package GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainScreen extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainStage.getInstance().setStage(primaryStage);
        primaryStage.show();

    }
}
