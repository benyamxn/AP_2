package GUI;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Point;

import java.util.Stack;

public class MainStage {
    private Stage stage;
    private Scene scene;
    private Stack<Parent> roots;
    private static MainStage instance = new MainStage();
    public void setStage(Stage stage) {
        this.stage = stage;
        scene = new Scene(new Group());
        stage.setTitle("Friendly Farm");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.fullScreenExitKeyProperty().setValue(KeyCombination.NO_MATCH);
        roots = new Stack<>();
    }

    public static MainStage getInstance() {
        return instance;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void popStack() {
        roots.pop();
        scene.setRoot(roots.peek());
    }

    public void pushStack(Parent root) {
        roots.push(root);
        scene.setRoot(root);
    }

    public double getWidth() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        return primaryScreenBounds.getWidth();
    }

    public double getHeight() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        return primaryScreenBounds.getHeight();
    }

}
