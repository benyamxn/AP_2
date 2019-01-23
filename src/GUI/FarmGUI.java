package GUI;

import controller.Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.*;
import model.Point;
import model.exception.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.LinkedList;

public class FarmGUI {
    public static double cellWidth;
    public static double cellHeight;
    private static final double startX = 185.0 / 800;
    private static final double startY = 200.0 / 600;
    private static final double endX = 605.0 / 800;
    private static final double endY = 480.0 / 600;
    private boolean pause = false;
    private Image image;
    public static AnchorPane anchorPane = new AnchorPane();
    private CellGUI[][] cellGUIs = new CellGUI[WIDTH][HEIGHT];
    private WorkshopGUI[] workshopGUIS = new WorkshopGUI[6];
    private Controller controller;
    private Farm farm;
    private Game game;
    private Timeline gameUpdater;
    private FarmCityView farmCityView;
    private static double[] size;
    public static Label debugLabel = new Label("");

    public static int WIDTH = 10;
    public static int HEIGHT = 10;

    public FarmGUI(Controller controller) throws FileNotFoundException {
        size = new double[]{MainStage.getInstance().getWidth(), MainStage.getInstance().getHeight()};
        cellWidth = (endX - startX) * size[0] / WIDTH;
        cellHeight = (endY - startY) * size[1] / HEIGHT;
        this.controller = controller;
        game = controller.getGame();
        game.getFarm().setFarmGUI(this);
        MainStage.getInstance().pushStack(anchorPane);
        farm = game.getFarm();
        loadBackground();
        createCellsGUI();
        for(int i = 0; i < 6 ; i++){
            int shift = 1;
            if(i > 2)
                shift = 0;
            Workshop temp = farm.getWorkshops()[i];
            workshopGUIS[i] = new WorkshopGUI(temp,false,200);
            double[] location = getPointForCell(temp.getProductionPoint().getWidth(),temp.getProductionPoint().getHeight());
            workshopGUIS[i].relocate(location[0] - cellWidth * 2 * shift, location[1] - 2 * cellHeight );
            workshopGUIS[i].addToRoot(anchorPane);
        }
        createGameStatus();
        renderAnimalBuyingButtons();
        createWellGUI();
        createTruckGUI();
        createHelicopterGUI();
        createGameUpdater();
        createFarmCityView();
        createWorkshopAction();
        createAnimalsGUI();
        createWarehouseGUI();
        debugLabel.setVisible(true);
        debugLabel.relocate(800,50);
        debugLabel.setText("sadfsdfasd");
        anchorPane.getChildren().add(debugLabel);
    }


    private void createAnimalsGUI(){
        Cell[][] cells = farm.getMap().getCells();
        for (int i = 0; i < farm.getMap().getWidth() ; i++) {
            for (int j = 0; j < farm.getMap().getHeight(); j++) {
                for (Animal animal : cells[i][j].getAnimals()) {
                    animal.setAnimalGUI(new AnimalGUI(animal));
                    animal.getAnimalGUI().initImages();
                    relocateAnimalGUI(animal.getAnimalGUI());
                }
            }
        }
    }

    private void createGameUpdater() {
        DurationManager durationManager = new DurationManager(this);
        gameUpdater = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            System.out.println("updated.");
            game.updateGame();
        }));
        gameUpdater.setRate(durationManager.getRate());
        gameUpdater.setCycleCount(Animation.INDEFINITE);
        gameUpdater.play();
        Button pauseButton = new Button("pause");
        pauseButton.relocate(700,50);

        Slider slider = new Slider(0.1,10,1);
        slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                durationManager.setRate(slider.getValue());
                System.out.println(slider.getValue());
            }
        });
        pauseButton.setOnMouseClicked(event -> {
            if(pause == false) {
                durationManager.pause();
                pause = true;
            } else {
                pause = false;
                durationManager.setRate(slider.getValue());
            }
        });

        slider.relocate(900,50);
        anchorPane.getChildren().addAll(pauseButton,slider);
    }

    private void loadBackground() throws FileNotFoundException {
        java.nio.file.Path cur = Paths.get(System.getProperty("user.dir"));
        java.nio.file.Path filePath = Paths.get(cur.toString(), "res", "backgrounds", "back.png");
        image = new Image(new FileInputStream(filePath.toString()));
    }

    private void createGameStatus() {
        game.getGameStatus().addToRoot(anchorPane);
        game.getGameStatus().relocate(2 * MainStage.getInstance().getWidth() / 3, 10);

    }

    private void createCellsGUI() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++){
                Cell cell = farm.getCell(new Point(i,j));
                double[] location = getPointForCell(i,j);
                cellGUIs[i][j] = new CellGUI(cell,cellWidth,cellHeight ,location);
                anchorPane.getChildren().add(cellGUIs[i][j].getImageView());
                cellGUIs[i][j].getImageView().relocate(location[0],location[1]);
                cellGUIs[i][j].render();
                cellGUIs[i][j].placeProduct(cell.getProducts());
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
                        System.out.println("not enough water");
                    }
                }
                else{
                    try {
                        farm.pickup(cell.getCoordinate());
                    } catch (NotEnoughCapacityException e) {
                       //TODO...
                        System.out.println("full warehouse fulll fulllllllll");
                    }
                }
            }
        });

        Button button = new Button("exit");

        button.relocate(500,50);
        button.setOnMouseClicked(event -> {
            try {
                String path = Paths.get(System.getProperty("user.dir"),"gameData","savedGames", "game6.json").toString();
                controller.saveGame(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        anchorPane.getChildren().add(button);
        MainStage.getInstance().getScene().getStylesheets().add(getClass().
                getResource("CSS/farm.css").toExternalForm());

    }


    private CellGUI getCellByEvent(double x, double y) {
        double width = size[0];
        double height = size[1];
        if( x >= startX * width  &&  x <= endX * width && y >= startY * height && y <= endY * height ){
            double mapWidth = endX * width - startX * width;
            double mapHeight = endY * height - startY * height;
            int cellWidth = (int) ((x - startX * width) / mapWidth * WIDTH);
            int cellHeight = (int) ((y - startY * height) / mapHeight * HEIGHT);
            return cellGUIs[cellWidth][HEIGHT - 1 - cellHeight];
        }
        return null;
    }

    public static double[] getPointForCell(int i, int j) {
        j = HEIGHT - 1 - j;
        double width = size[0];
        double height = size[1];
        double mapWidth = endX * width - startX * width;
        double mapHeight = endY * height - startY * height;
        double cellWidth = (startX * width + i * mapWidth /  WIDTH);
        double cellHeight = (startY * height + j * mapHeight /  HEIGHT );
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
                    System.out.println(e.getMessage());
                    // TODO: Handle this.
                }

            });
        }
    }

    private void createTruckGUI() {
        VehicleGUI truckGUI = new VehicleGUI(farm.getTruck(), (int) (MainStage.getInstance().getWidth() / 10));
        truckGUI.setOnClick(event -> {
            new TruckMenu(game);
        });
        truckGUI.relocate(MainStage.getInstance().getWidth() / 5, MainStage.getInstance().getHeight() * 0.85);
        truckGUI.addToRoot(anchorPane);
    }

    private void createWarehouseGUI() {
        WarehouseGUI warehouseGUI = farm.getWarehouse().getWarehouseGUI();
        warehouseGUI.setOnClick(event -> new TruckMenu(game));
        warehouseGUI.relocate(2 * MainStage.getInstance().getWidth() / 5, MainStage.getInstance().getHeight() * 0.83);
        warehouseGUI.addToRoot(anchorPane);
    }

    private void createHelicopterGUI() {
        VehicleGUI HelicopterGUI = new VehicleGUI(farm.getHelicopter(), (int) (MainStage.getInstance().getWidth() / 10));
        HelicopterGUI.relocate(MainStage.getInstance().getWidth() * 0.7, MainStage.getInstance().getHeight() * 0.85);
        HelicopterGUI.addToRoot(anchorPane);
    }

    private void createFarmCityView() {
        farmCityView = FarmCityView.getInstance(game, MainStage.getInstance().getWidth() * 0.2);
        farmCityView.relocate(MainStage.getInstance().getWidth() * 0.8, 0);
        farmCityView.addToRoot(anchorPane);
    }

    public void createWorkshopAction(){
        for (WorkshopGUI workshop : workshopGUIS) {
            workshop.setOnClick(event -> {
                try {
                    controller.startWorkshop(workshop.getWorkshop().getName());
                } catch (NameNotFoundException e) {

                } catch (NotEnoughItemsException e) {
                    System.out.println("NotEnoughItemsException");
                }
            });
        }
    }

    public Farm getFarm() {
        return farm;
    }

    public FarmCityView getFarmCityView() {
        return farmCityView;
    }

    public CellGUI[][] getCellGUIs() {
        return cellGUIs;
    }

    public WorkshopGUI[] getWorkshopGUIS() {
        return workshopGUIS;
    }

    public Timeline getGameUpdater() {
        return gameUpdater;
    }
}
