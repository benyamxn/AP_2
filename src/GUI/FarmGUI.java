package GUI;

import controller.Controller;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.*;
import model.exception.MoneyNotEnoughException;
import model.exception.NameNotFoundException;
import model.exception.NotEnoughCapacityException;
import model.exception.NotEnoughWaterException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class FarmGUI {

    public static double cellWidth;
    public static double cellHeight;
    private static final double startX = 185.0 / 800;
    private static final double startY = 200.0 / 600;
    private static final double endX = 605.0 / 800;
    private static final double endY = 480.0 / 600;
    private Image image;
    private static AnchorPane anchorPane = new AnchorPane();
    private CellGUI[][] cellGUIs = new CellGUI[30][30];
    private WorkshopGUI[] workshopGUIS = new WorkshopGUI[7];
    private Controller controller;
    private Farm farm;
    private Game game;
    private Timer gameUpdater;
    FarmGUI(Controller controller) throws FileNotFoundException, MoneyNotEnoughException {
        this.controller = controller;
        game = controller.getGame();
        game.getFarm().setFarmGUI(this);
        MainStage.getInstance().pushStack(anchorPane);
        farm = game.getFarm();
        loadBackground();
        createCellsGUI();
        for(int i = 0; i < 1; i++){
            workshopGUIS[i] = new WorkshopGUI(farm.getWorkshops()[i],true);
            anchorPane.getChildren().add(workshopGUIS[i].getImageView());
            workshopGUIS[i].getImageView().relocate(1200,300);
        }
        for (int i = 0; i < 3; i++) {
            for (int i1 = 0; i1 < 3; i1++) {
                placeProduct(new Product(ProductType.EGG), 10 + i, 10 + i1);
            }
        }
        createGameStatus();
        farm.placeAnimal(new Cat(new Point(0,0)));
        renderAnimalBuyingButtons();
        createWellGUI();
        createTruckGUI();
        createHelicopterGUI();
        createGameUpdater();
        cellWidth = (endX - startX) * anchorPane.getWidth() / 30;
        cellHeight = (endY - startY) * anchorPane.getHeight() / 30;
    }

    private void createGameUpdater() {
        gameUpdater = new Timer(true);
        gameUpdater.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("here");
                Platform.runLater(() -> game.updateGame());
            }
        }, 0, 2000);
    }

    private void loadBackground() throws FileNotFoundException {
        Path cur = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(cur.toString(), "res", "backgrounds", "back.png");
        image = new Image(new FileInputStream(filePath.toString()));
    }

    private void createGameStatus() {
        game.getGameStatus().addToRoot(anchorPane);
        game.getGameStatus().relocate(2 * MainStage.getInstance().getWidth() / 3, 10);
    }

    private void createCellsGUI() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++){
                cellGUIs[i][j] = new CellGUI(farm.getCell(new Point(i,j)));
                anchorPane.getChildren().add(cellGUIs[i][j].getImageView());
                cellGUIs[i][j].getImageView().relocate(getPointForCell(i, j)[0],getPointForCell(i, j)[1]);
            }
        }
    }

    private void createWellGUI() {
        WellGUI wellGUI = new WellGUI(game.getFarm().getWell(), (int) (MainStage.getInstance().getWidth() / 8));
        game.getFarm().getWell().setWellGUI(wellGUI);
        wellGUI.addToRoot(anchorPane);
        wellGUI.relocate(2.4 * MainStage.getInstance().getWidth() / 5, 3 * MainStage.getInstance().getHeight() / 40);
        wellGUI.setOnClick(event -> {
            try {
                game.well();
            } catch (MoneyNotEnoughException e) {
                e.printStackTrace();
                // TODO: Handle this
            }
        });
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
                        int[] grassLevels = new int[9];

                        LinkedList<Cell> cellsToPlant = farm.getMap().getCellsForPlant(cellGUI.getCell().getCoordinate());
                        for (int i = 0; i < cellsToPlant.size(); i++) {
                            grassLevels[i] = cellsToPlant.get(i).getGrassLevel();
                        }

                        farm.plant(cell.getCoordinate());

                        for (int i = 0; i < cellsToPlant.size(); i++) {
                            Cell cell1 = cellsToPlant.get(i);
                            cellGUIs[cell1.getCoordinate().getWidth()][cell1.getCoordinate().getHeight()].growGrass(grassLevels[i]);
                        }
                    } catch (NotEnoughWaterException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else{
                    try {
                        farm.pickup(cell.getCoordinate());
                    } catch (NotEnoughCapacityException e) {
                       //TODO...
                        System.out.println(e.getMessage());
                    }
                }
            }
        });

        MainStage.getInstance().getScene().getStylesheets().add(getClass().
                getResource("CSS/farm.css").toExternalForm());

    }


    private CellGUI getCellByEvent(double x, double y) {
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

    public static double[] getPointForCell(int i, int j) {
        double width = anchorPane.getBoundsInParent().getWidth();
        double height = anchorPane.getBoundsInParent().getHeight();
        double mapWidth = endX * width - startX * width;
        double mapHeight = endY * height - startY * height;
        double cellWidth = (startX * width + i * mapWidth / 30);
        double cellHeight = (startY * height + j * mapHeight / 30);
        return new double[]{cellWidth, cellHeight};
    }

    private void placeProduct(Product product, int x, int y) throws FileNotFoundException {
        ProductGUI productGUI = new ProductGUI(product, 1);
        productGUI.getImageView().relocate(getPointForCell(x, y)[0], getPointForCell(x, y)[1]);
        anchorPane.getChildren().add(productGUI.getImageView());
    }

    public void relocateAnimalGUI(AnimalGUI animalGUI) {
        Point location = animalGUI.getAnimal().getLocation();
        double[] pointForCell = getPointForCell(location.getWidth(), location.getHeight());
        animalGUI.getImageView().relocate(pointForCell[0], pointForCell[1]);
        anchorPane.getChildren().add(animalGUI.getImageView());
    }

    private void renderAnimalBuyingButtons() {
        double startX = 10;
        double startY = 10;
        int radius = 50;
        double marginDistance = 10;
        Point dummyPoint = new Point(0, 0);
        // TODO: handle constants here.
        AnimalBuyButton[] animalBuyButtons = new AnimalBuyButton[5];
        for (int i = 0; i < 3; i++) {
            animalBuyButtons[i] = new AnimalBuyButton(radius, new Domesticated(dummyPoint, farm.getDomesticatedAnimals()[i]));
        }
        animalBuyButtons[3] = new AnimalBuyButton(radius, new Cat(dummyPoint));
        animalBuyButtons[4] = new AnimalBuyButton(radius, new Dog(dummyPoint));
        for (int i = 0; i < 5; i++) {
            animalBuyButtons[i].addToRoot(anchorPane);
            animalBuyButtons[i].relocateInRoot(startX, startY);
            startX += 2 * radius + marginDistance;
            final String animalName = animalBuyButtons[i].getAnimal().toString();
            animalBuyButtons[i].setOnClick(event -> {
                try {
                    controller.buyAnimal(animalName);
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                } catch (MoneyNotEnoughException e) {
                    e.printStackTrace();
                    // TODO: Handle this.
                }

            });
        }
    }

    private void createTruckGUI() {
        VehicleGUI truckGUI = new VehicleGUI(farm.getTruck(), (int) (MainStage.getInstance().getWidth() / 10));
        truckGUI.relocate(MainStage.getInstance().getWidth() / 5, MainStage.getInstance().getHeight() * 0.7);
        truckGUI.addToRoot(anchorPane);
    }

    private void createHelicopterGUI() {
        VehicleGUI HelicopterGUI = new VehicleGUI(farm.getHelicopter(), (int) (MainStage.getInstance().getWidth() / 10));
        HelicopterGUI.relocate(MainStage.getInstance().getWidth() * 0.7, MainStage.getInstance().getHeight() * 0.7);
        HelicopterGUI.addToRoot(anchorPane);
    }

}
