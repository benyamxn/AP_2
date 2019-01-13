package GUI;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import model.Farm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FarmGUI {
    private Image image;

    FarmGUI() throws FileNotFoundException {
        Path cur = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(cur.toString(), "res", "map", "farm.png");
        image = new Image(new FileInputStream(filePath.toString()));
    }

    public void render() {
        ImageView imageView = new ImageView(image);

        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-image: url(" + image.getUrl() + ")" );
        Group root = new Group();
        root.getChildren().add(stackPane);
        MainStage.getInstance().pushStack(root);
    }

}
