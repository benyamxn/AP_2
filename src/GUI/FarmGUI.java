package GUI;

import javafx.geometry.Insets;
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
    private StackPane stackPane = new StackPane();
    private CellGUI[][] cellGUIs = new CellGUI[30][30];
    FarmGUI() throws FileNotFoundException {
        Path cur = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(cur.toString(), "res", "backgrounds", "back.png");
        image = new Image(new FileInputStream(filePath.toString()));
    }

    public void render() {
        ImageView imageView = new ImageView(image);
        stackPane.setId("farmPane");

//
//        stackPane.setOnMouseClicked(e);
        MainStage.getInstance().getScene().getStylesheets().add(getClass().
                getResource("CSS/farm.css").toExternalForm());
        MainStage.getInstance().pushStack(stackPane);


    }
//
//    public CellGUI getCellByEvent(double x, double y){
//
//        if( x >  && )
//
//    }

}
