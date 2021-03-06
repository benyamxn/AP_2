package GUI;

import GUI.animation.ZoomAnimation;
import controller.Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;
import model.*;
import model.Cell;
import model.Point;
import model.exception.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.LinkedList;

public class FarmGUI {
    public static double cellWidth;
    public static double cellHeight;
    protected static final double startX = 185.0 / 800;
    protected static final double startY = 200.0 / 600;
    protected static final double endX = 605.0 / 800;
    protected static final double endY = 480.0 / 600;
    protected boolean pause = false;
    protected Image image;
    public static AnchorPane anchorPane = new AnchorPane();
    protected CellGUI[][] cellGUIs = new CellGUI[WIDTH][HEIGHT];
    protected WorkshopGUI[] workshopGUIS = new WorkshopGUI[6];
    protected Controller controller;
    protected Farm farm;
    protected Game game;
    protected Timeline gameUpdater;
    protected FarmCityView farmCityView;
    protected Rectangle pauseRectangle;
    protected GameMenuGUI gameMenuGUI;
    protected static SoundUI soundPlayer;
    protected static double[] size;
    public static Label debugLabel = new Label("");
    public static int WIDTH = 10;
    public static int HEIGHT = 10;
    protected DurationManager durationManager;
    protected MissionGUI missionGUI;

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
            workshopGUIS[i] = new WorkshopGUI(temp,false,200, this);
            double[] location = getPointForCell(temp.getProductionPoint().getWidth(),temp.getProductionPoint().getHeight());
            workshopGUIS[i].relocate(location[0] - cellWidth * 2 * shift, location[1] - 2 * cellHeight );
            workshopGUIS[i].addToRoot(anchorPane);
            UpgradeButton upgradeButton = new UpgradeButton(workshopGUIS[i].getWorkshop());
            WorkshopGUI thisGUI = workshopGUIS[i];
            upgradeButton.setOnClick(mouseEvent -> {
                try {
                    controller.upgrade(thisGUI.getWorkshop());
                    upgradeButton.render();
                } catch (MoneyNotEnoughException e) {
                    e.printStackTrace();
                } catch (MaxLevelException e) {
                    e.printStackTrace();
                }
            });
            upgradeButton.addToRoot(anchorPane);
            upgradeButton.relocate(location[0] - cellWidth * 2 * shift - 250 * (shift - 0.8), location[1] - 2 * cellHeight + 0.1 * MainStage.getInstance().getHeight());
        }
        createSoundPlayer();
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
        createCamera();
        createMenu();
        createPausePage();
        createMissionGUI();
        durationManager = new DurationManager(this);
        gameMenuGUI = new GameMenuGUI(this);
    }

    protected void createMissionGUI() {
        missionGUI = new MissionGUI(game);
        missionGUI.addToRoot(anchorPane);
    }

    protected void createMenu(){
        Button button = new Button("Pause");
        Hoverable.setMouseHandler(button);
        button.relocate(button.getWidth(),MainStage.getInstance().getHeight() * 0.95);
        button.setOnMouseClicked(event -> {
            createPausePage();
            soundPlayer.playTrack("click");
            pauseFarm();
            anchorPane.getChildren().add(pauseRectangle);
            gameMenuGUI.show();
        });
        anchorPane.getChildren().add(button);
    }

    public void pauseFarm(){
        gameUpdater.pause();
        durationManager.pause();
        pause = true;
    }

    public void resume(){
        durationManager.resume();
        gameUpdater.play();
        pause = false;
    }


    protected void createPausePage() {
        double width, height;
        width = MainStage.getInstance().getWidth() + 100;
        height = MainStage.getInstance().getHeight() + 100;
        pauseRectangle = new Rectangle(0, 0, width, height);
        pauseRectangle.setFill(Color.GRAY);
        pauseRectangle.setOpacity(0.5);
    }


    protected void createCamera(){
        ZoomAnimation zoomAnimation = new ZoomAnimation();
        anchorPane.setOnScroll(event -> {
            zoomAnimation.zoom(anchorPane,Math.pow(1.01,event.getDeltaY()),event.getSceneX(),event.getSceneY(), Screen.getPrimary().getBounds());
        });
        final double[] mouseX = new double[1];
        final double[] mouseY = new double[1];
        anchorPane.setOnMousePressed(e -> {
            mouseX[0] = e.getScreenX();
            mouseY[0] = e.getScreenY();
        });
        anchorPane.setOnMouseDragged(e -> {
            double deltaX = e.getScreenX() - mouseX[0];
            double deltaY = e.getScreenY() - mouseY[0];
            zoomAnimation.drag(anchorPane,deltaX,deltaY,Screen.getPrimary().getBounds());
            mouseX[0] = e.getScreenX();
            mouseY[0] = e.getScreenY();
        });
    }

    protected void createAnimalsGUI(){
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

    protected void createGameUpdater() {
        DurationManager durationManager = new DurationManager(this);
        gameUpdater = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            game.updateGame();
            missionGUI.updateMoney();
            missionGUI.updateProducts();
            if(game.isFinished()){
                System.out.println("level finished");
                createPausePage();
                pauseFarm();
                anchorPane.getChildren().add(pauseRectangle);
                gameMenuGUI.showExit(false,null,null);
            }

        }));
        gameUpdater.setRate(durationManager.getRate());
        gameUpdater.setCycleCount(Animation.INDEFINITE);
        gameUpdater.play();

        createGameSpeedSlider(durationManager);
    }

    protected void createGameSpeedSlider(DurationManager durationManager) {
        Slider gameSpeedSlider = new Slider(0.2, 10, gameUpdater.getRate());
        gameSpeedSlider.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                durationManager.setRate(newValue.doubleValue());
            }
        });
        gameSpeedSlider.setScaleX(1.5);
        gameSpeedSlider.setScaleY(1.5);
        gameSpeedSlider.relocate(MainStage.getInstance().getWidth() / 2 + 100 ,25);

        Text text = new Text("Speed :");
        text.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        text.relocate(MainStage.getInstance().getWidth() / 2, 20);
        anchorPane.getChildren().addAll(gameSpeedSlider, text);
    }

    protected void loadBackground() throws FileNotFoundException {
        java.nio.file.Path cur = Paths.get(System.getProperty("user.dir"));
        java.nio.file.Path filePath = Paths.get(cur.toString(), "res", "backgrounds", "back.png");
        image = new Image(new FileInputStream(filePath.toString()));
    }

    protected void createGameStatus() {
        game.getGameStatus().addToRoot(anchorPane);
        game.getGameStatus().relocate(2 * MainStage.getInstance().getWidth() / 3, 10);

    }

    protected void createCellsGUI() {
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

    protected void createWellGUI() {
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
        UpgradeButton upgradeButton = new UpgradeButton(wellGUI.getWell());
        upgradeButton.setOnClick(event -> {
            try {
                controller.upgrade(wellGUI.getWell());
                upgradeButton.render();
            } catch (MoneyNotEnoughException e) {
                e.printStackTrace();
            } catch (MaxLevelException e) {
                e.printStackTrace();
            }
        });
        upgradeButton.addToRoot(anchorPane);
        upgradeButton.relocate(2.4 * MainStage.getInstance().getWidth() / 5 - 40, 7 * MainStage.getInstance().getHeight() / 40);
    }

    public void render() {

        ImageView imageView = new ImageView(image);
        anchorPane.setId("farmPane");
        anchorPane.setOnMouseClicked(event -> {
            if(pause){
                return;
            }
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
                        soundPlayer.playTrack("water");
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
        try {
            MainStage.getInstance().getScene().getStylesheets().add(getClass().getResource("CSS/farm.css").toExternalForm());
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }


    }

    protected CellGUI getCellByEvent(double x, double y) {
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

    protected void placeProduct(Product product, int x, int y) throws FileNotFoundException {
        ProductGUI productGUI = new ProductGUI(product, 1);
        productGUI.getImageView().relocate(getPointForCell(x, y)[0], getPointForCell(x, y)[1]);
        anchorPane.getChildren().add(productGUI.getImageView());
    }

    public void relocateAnimalGUI(AnimalGUI animalGUI) {
        Point location = animalGUI.getAnimal().getLocation();
        double[] pointForCell = getPointForCell(location.getWidth(), location.getHeight());
        animalGUI.relocate(pointForCell[0], pointForCell[1]);
        animalGUI.addToRoot(anchorPane);
    }

    protected void renderAnimalBuyingButtons() {
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
        UpgradeButton upgradeButton = new UpgradeButton(new Cat(dummyPoint));
        upgradeButton.setOnClick(event -> {
            try {
                FarmGUI.getSoundPlayer().playTrack("cat");
                controller.upgrade(new Cat(dummyPoint));
                upgradeButton.render();
            } catch (MoneyNotEnoughException e) {
                e.printStackTrace();
            } catch (MaxLevelException e) {
                e.printStackTrace();
            }
        });
        animalBuyButtons[4] = new AnimalBuyButton(radius, new Dog(dummyPoint));
        for (int i = 0; i < 5; i++) {
            animalBuyButtons[i].addToRoot(anchorPane);
            animalBuyButtons[i].relocateInRoot(startX, startY);
            if (i == 3) {
                upgradeButton.addToRoot(anchorPane);
                upgradeButton.relocate(startX + radius - 30, startY + 2 * radius + 45);
            }
            startX += 2 * radius + marginDistance;
            final String animalName = animalBuyButtons[i].getAnimal().toString().toLowerCase();
            animalBuyButtons[i].setOnClick(event -> {
                try {
                    controller.buyAnimal(animalName);
                    if (animalName.equals("cat") || animalName.equals("dog"))
                        soundPlayer.playTrack(animalName);
                    else
                        soundPlayer.playTrack(animalName + " produce");
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                } catch (MoneyNotEnoughException e) {
                    System.out.println(e.getMessage());
                    // TODO: Handle this.
                }

            });
        }
    }

    protected void createTruckGUI() {
        VehicleGUI truckGUI = new VehicleGUI(farm.getTruck(), (int) (MainStage.getInstance().getWidth() / 10));
        truckGUI.setOnClick(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            pauseFarm();
            new TruckMenu(game,this);
        });
        UpgradeButton upgradeButton = createVehicleUpgradeButton(truckGUI);
        truckGUI.relocate(MainStage.getInstance().getWidth() / 5, MainStage.getInstance().getHeight() * 0.85);
        upgradeButton.relocate(MainStage.getInstance().getWidth() / 5 - 60, MainStage.getInstance().getHeight()* 0.90);
        truckGUI.addToRoot(anchorPane);
    }

    protected void createHelicopterGUI() {
        VehicleGUI helicopterGUI = new VehicleGUI(farm.getHelicopter(), (int) (MainStage.getInstance().getWidth() / 10));
        helicopterGUI.relocate(MainStage.getInstance().getWidth() * 0.7, MainStage.getInstance().getHeight() * 0.85);
        UpgradeButton upgradeButton = createVehicleUpgradeButton(helicopterGUI);
        helicopterGUI.setOnClick(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            pauseFarm();
            new HelicopterMenu(game,this);

        });
        upgradeButton.relocate(MainStage.getInstance().getWidth() * 0.7 - 60, MainStage.getInstance().getHeight()* 0.90);
        helicopterGUI.addToRoot(anchorPane);
    }

    protected void createWarehouseGUI() {
        WarehouseGUI warehouseGUI = farm.getWarehouse().getWarehouseGUI();
        warehouseGUI.setOnClick(event -> {
            pauseFarm();
            FarmGUI.getSoundPlayer().playTrack("click");
            new TruckMenu(game,this);
        });
        warehouseGUI.relocate(2 * MainStage.getInstance().getWidth() / 5, MainStage.getInstance().getHeight() * 0.83);
        warehouseGUI.addToRoot(anchorPane);
        UpgradeButton upgradeButton = new UpgradeButton(warehouseGUI.getWarehouse());
        upgradeButton.setOnClick(mouseEvent -> {
            try {
                controller.upgrade(warehouseGUI.getWarehouse());
                upgradeButton.render();
            } catch (MoneyNotEnoughException e) {
                e.printStackTrace();
            } catch (MaxLevelException e) {
                e.printStackTrace();
            }
        });
        upgradeButton.addToRoot(anchorPane);
        upgradeButton.relocate(2 * MainStage.getInstance().getWidth() / 5 - 50, MainStage.getInstance().getHeight() * 0.9);
    }

    protected void createFarmCityView() {
        farmCityView = FarmCityView.getInstance(game, MainStage.getInstance().getWidth() * 0.2);
        farmCityView.relocate(MainStage.getInstance().getWidth() * 0.8, 0);
        farmCityView.addToRoot(anchorPane);
    }

    protected void createWorkshopAction(){
        for (WorkshopGUI workshopGUI : workshopGUIS) {
            workshopGUI.setOnClick(event -> {
                try {
                    if (event.getButton().equals(MouseButton.PRIMARY))
                        controller.startWorkshop(workshopGUI.getWorkshop().getName());
                } catch (NameNotFoundException e) {

                } catch (NotEnoughItemsException e) {
                    System.out.println("NotEnoughItemsException");
                }
            });

            Workshop workshop = workshopGUI.getWorkshop();
            Tooltip tooltip = new Tooltip(workshop.getName().replace("_", " "));
            workshopGUI.initTooltip(tooltip);
            workshopGUI.setOnHold(event -> {
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    pauseFarm();
                    anchorPane.getChildren().add(pauseRectangle);
                    workshopGUI.show(anchorPane);
                }
            });
        }
    }

    protected void createSoundPlayer() {
        soundPlayer = MainStage.getInstance().getSoundUI();
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

    public Controller getController() {
        return controller;
    }

    public static SoundUI getSoundPlayer() {
        return soundPlayer;
    }

    protected UpgradeButton createVehicleUpgradeButton(VehicleGUI vehicleGUI) {
        UpgradeButton upgradeButton = new UpgradeButton(vehicleGUI.getVehicle());
        upgradeButton.setOnClick(event -> {
            try {
                FarmGUI.getSoundPlayer().playTrack(vehicleGUI.getVehicle().toString().toLowerCase() + " upgrade");
                controller.upgrade(vehicleGUI.getVehicle());
                vehicleGUI.upgrade();
                upgradeButton.render();
            } catch (MoneyNotEnoughException e) {
                e.printStackTrace();
            } catch (MaxLevelException e) {
                e.printStackTrace();
            }
        });
        upgradeButton.addToRoot(anchorPane);
        return upgradeButton;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public Rectangle getPauseRectangle() {
        return pauseRectangle;
    }

    public Game getGame() {
        return game;
    }
}
