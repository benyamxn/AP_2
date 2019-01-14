package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.Cell;
import model.Farm;
import model.Game;
import model.Point;
import model.exception.MoneyNotEnoughException;
import model.exception.NotEnoughCapacityException;
import model.exception.NotEnoughWaterException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class FarmGUI {

    private static final double startX = 185.0 / 800;
    private static final double startY = 200.0 / 600;
    private static final double endX = 605.0 / 800;
    private static final double endY = 480.0 / 600;
    private Image image;
    private AnchorPane anchorPane = new AnchorPane();
    private CellGUI[][] cellGUIs = new CellGUI[30][30];
    private WorkshopGUI[] workshopGUIS = new WorkshopGUI[7];
    private Farm farm;
    private Game game;
    FarmGUI(Game game) throws FileNotFoundException, MoneyNotEnoughException {
        MainStage.getInstance().pushStack(anchorPane);
        this.game = game;
        farm = game.getFarm();
        Path cur = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(cur.toString(), "res", "backgrounds", "back.png");
        image = new Image(new FileInputStream(filePath.toString()));
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++){
                cellGUIs[i][j] = new CellGUI(farm.getCell(new Point(i,j)));
                anchorPane.getChildren().add(cellGUIs[i][j].getImageView());
                cellGUIs[i][j].getImageView().relocate(getPointForCell(i, j)[0],getPointForCell(i, j)[1]);
            }
        }
        for(int i = 0; i < 1; i++){
            workshopGUIS[i] = new WorkshopGUI(farm.getWorkshops()[i],true);
            anchorPane.getChildren().add(workshopGUIS[i].getImageView());
            workshopGUIS[i].getImageView().relocate(1200,300);
        }
        game.well();


    }

    public void render() {

        ImageView imageView = new ImageView(image);
        anchorPane.setId("farmPane");
        anchorPane.setOnMouseClicked(event -> {
            CellGUI cellGUI = getCellByEvent(event.getX(), event.getY());
            if(cellGUI != null){
                Cell cell = cellGUI.getCell();
                if(!cell.hasProduct()) {
                    try {
                        LinkedList<Cell> cells =(LinkedList<Cell>) farm.getMap().getCellsForPlant(cellGUI.getCell().getCoordinate()).clone();
                        farm.plant(cell.getCoordinate());
                        for (Cell cell1 : cells) {
                            cellGUIs[cell1.getCoordinate().getWidth()][cell1.getCoordinate().getHeight()].growGrass();
                        }
                    } catch (NotEnoughWaterException e) {
                        e.printStackTrace();
                    }
                }
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

    }


    private CellGUI getCellByEvent(double x, double y){
        double width = anchorPane.getBoundsInParent().getWidth();
        double height = anchorPane.getBoundsInParent().getHeight();
        if( x >= startX * width  &&  x <= endX * width && y >= startY * height && y <= endY * height ){
            double mapWidth = endX * width - startX * width;
            double mapHeight = endY * height - startY * height;
            int cellWidth = (int) ((x - startX * width) / mapWidth * 30.0);
            int cellHeight = (int) ((y - startY * height) / mapHeight * 30.0);
            return cellGUIs[cellWidth][cellHeight];
        }
        return null;
    }

    private double[] getPointForCell(int i, int j) {
        double width = anchorPane.getBoundsInParent().getWidth();
        double height = anchorPane.getBoundsInParent().getHeight();
        double mapWidth = endX * width - startX * width;
        double mapHeight = endY * height - startY * height;
        double cellWidth = (startX * width + i * mapWidth / 30);
        double cellHeight = (startY * height + j * mapHeight / 30);
        return new double[]{cellWidth, cellHeight};
    }


}
