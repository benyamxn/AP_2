package GUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import model.Cell;
import model.Farm;
import model.exception.NotEnoughCapacityException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FarmGUI {

    private static final double startX = 220.0 / 800;
    private static final double startY = 195.0 / 600;
    private static final double endX = 595.0 / 800;
    private static final double endY = 450.0 / 600;
    private Image image;
    private StackPane stackPane = new StackPane();
    private CellGUI[][] cellGUIs = new CellGUI[30][30];
    private Farm farm;
    FarmGUI(Farm farm) throws FileNotFoundException {
        Path cur = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(cur.toString(), "res", "backgrounds", "back.png");
        image = new Image(new FileInputStream(filePath.toString()));
        this.farm = farm;
    }

    public void render() {
        ImageView imageView = new ImageView(image);
        stackPane.setId("farmPane");
        stackPane.setOnMouseClicked(event -> {
            CellGUI cellGUI = getCellByEvent(event.getX(), event.getY());

            if(cellGUI != null){
                Cell cell = cellGUI.getCell();
                if(!cell.hasProduct())
                      cellGUI.growGrass();
                else{
                    try {
                        farm.pickup(cell.getCoordinate());
                    } catch (NotEnoughCapacityException e) {
                       //TODO....
                        e.printStackTrace();
                    }
                }
            }
        });
        MainStage.getInstance().getScene().getStylesheets().add(getClass().
                getResource("CSS/farm.css").toExternalForm());
        MainStage.getInstance().pushStack(stackPane);
    }


    public CellGUI getCellByEvent(double x, double y){
        double width = stackPane.getWidth();
        double height = stackPane.getHeight();
        if( x > startX * width  &&  x <= endX * width && y > startY * height && y < endY * height ){
            double mapWidth = (endX - startX) * width;
            double mapHeight = (endY - startY) * height;
            int cellWidth = (int) ((x - startX * width) / mapWidth * 30);
            int cellHeight = (int) ((y - startY * height) / mapHeight * 30);
            return cellGUIs[cellWidth][cellHeight];
        }
        return null;
    }

}
