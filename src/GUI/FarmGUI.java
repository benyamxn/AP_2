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
import model.Game;
import model.Point;
import model.exception.MoneyNotEnoughException;
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
    private Game game;
    FarmGUI(Game game) throws FileNotFoundException, MoneyNotEnoughException {
        this.game = game;
        farm = game.getFarm();
        Path cur = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(cur.toString(), "res", "backgrounds", "back.png");
        image = new Image(new FileInputStream(filePath.toString()));
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++){
                cellGUIs[i][j] = new CellGUI(farm.getCell(new Point(i,j)));
                stackPane.getChildren().add(cellGUIs[i][j].getImageView());
                cellGUIs[i][j].getImageView().relocate(getPointForCell(i, j)[0],getPointForCell(i, j)[1]);
            }
        }
        game.well();
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


    private CellGUI getCellByEvent(double x, double y){
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

    private double[] getPointForCell(int i, int j) {
        double width = stackPane.getWidth();
        double height = stackPane.getHeight();
        double mapWidth = (endX - startX) * width;
        double mapHeight = (endY - startY) * height;
        double cellWidth = (i * mapWidth / 30 + startX * width);
        double cellHeight = ((j + startY * height + mapHeight / 30));
        return new double[]{cellWidth, cellHeight};
    }


}
