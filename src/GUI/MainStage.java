package GUI;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.util.Stack;

public class MainStage {
    private Stage stage;
    private Scene scene;
    private Stack<Group> roots;
    private static MainStage instance = new MainStage();
    public void setStage(Stage stage) {
        this.stage = stage;
        scene = new Scene(null);
        stage.setTitle("Friendly Farm");
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.fullScreenExitKeyProperty().setValue(KeyCombination.NO_MATCH);
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

    public void pushStack(Group root) {
        roots.push(root);
        scene.setRoot(root);
    }

}
