package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.LinkedList;

public class FarmCityView {
    private Group root;
    private Image image;
    private ImageView imageView;
    private Game game;
    private double width;
    private double factor = 2000;
    private double durationOfAnimation = 100;
    private LinkedList<Animation> animations = new LinkedList<>();
    private static FarmCityView instance;
    private double rate = 0.5;

    private FarmCityView(Game game, double width) {
        root = new Group();
        this.game = game;
        this.width = width;
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","backgrounds",
                    "cityFarm.jpg").toString()));
            imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(event -> {
                runTruck(100);
                runHelicopter();
                rate *= 2;
                for (Animation animation : animations) {
                    animation.setRate(rate);
                }
            });
            root.getChildren().add(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static FarmCityView getInstance(Game game, double width) {
        if (instance == null) {
            instance = new FarmCityView(game, width);
        }
        return instance;
    }

    public static FarmCityView getInstance() {
        return instance;
    }

    public void relocate(double x, double y) {
        imageView.relocate(x, y);
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().add(root);
    }

    public void runTruck(int money) {
        Image vehicleImage;
        double vehicleHeight, imageHeight;
        imageHeight = image.getHeight() * (width / image.getWidth());
        double duration = game.getFarm().getTruck().getArrivalTime() * factor;
        try {
            HBox moneyBox = createPriceLabel(money);
            vehicleImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures","UI",
                    "Truck", "0" + game.getFarm().getTruck().getLevel() + "_mini.png").toString()));
            int columnNumber = AnimationConstants.TRUCK_MINI[0];
            int rowNumber = AnimationConstants.TRUCK_MINI[1] / AnimationConstants.TRUCK_MINI[0];
            double truckWidth = 50;
            double spriteWidth = vehicleImage.getWidth() / columnNumber;
            double spriteHeight = vehicleImage.getHeight() /rowNumber;
            ImageView vehicleView = new ImageView(vehicleImage);
            vehicleView.setFitWidth(truckWidth);
            vehicleHeight = vehicleImage.getHeight() * (truckWidth * columnNumber / rowNumber / vehicleImage.getWidth());
            vehicleView.setPreserveRatio(true);
            vehicleView.setViewport(new Rectangle2D(0, 0, spriteWidth, spriteHeight));
            vehicleView.setScaleX(-1);
            vehicleView.setOpacity(1);
            root.getChildren().addAll(moneyBox, vehicleView);
            vehicleView.relocate(MainStage.getInstance().getWidth() - width, imageHeight - vehicleHeight - 15);
            moneyBox.relocate(MainStage.getInstance().getWidth() - width, imageHeight - 20);
            Animation animation = new SpriteAnimation(vehicleView,
                    Duration.millis(durationOfAnimation), AnimationConstants.TRUCK_MINI[1],
                    AnimationConstants.TRUCK_MINI[0], 0, 0, (int) spriteWidth, (int) spriteHeight);
            animation.setCycleCount((int) (duration / 2 / durationOfAnimation));


            TranslateTransition translateTransitionGo = new TranslateTransition(Duration.millis(duration / 2), vehicleView);
            translateTransitionGo.setByY(0);
            translateTransitionGo.setByX(width - truckWidth);

            animations.add(translateTransitionGo);

            TranslateTransition translateTransitionReturn = new TranslateTransition(Duration.millis(duration / 2), vehicleView);
            translateTransitionReturn.setByY(0);
            translateTransitionReturn.setByX(truckWidth - width);

            animations.add(translateTransitionReturn);

            TranslateTransition moneyGo = new TranslateTransition(Duration.millis(duration / 2), moneyBox);
            moneyGo.setByY(0);
            moneyGo.setByX(width - truckWidth);

            TranslateTransition moneyReturn = new TranslateTransition(Duration.millis(duration / 2), moneyBox);
            moneyReturn.setByX(truckWidth - width);
            moneyReturn.setByY(0);

            translateTransitionGo.setOnFinished(event -> {
                vehicleView.setScaleX(1);
                translateTransitionReturn.play();
                animation.play();
                moneyReturn.play();
            });

            translateTransitionReturn.setOnFinished(event -> root.getChildren().removeAll(vehicleView, moneyBox));

            animation.play();
            translateTransitionGo.play();
            moneyGo.play();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void runHelicopter() {
        Image vehicleImage;
        double duration = game.getFarm().getHelicopter().getArrivalTime() * factor;
        try {
            vehicleImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures","UI",
                    "Helicopter", "0" + game.getFarm().getHelicopter().getLevel() + "_mini.png").toString()));
            int columnNumber = AnimationConstants.HELICOPTER_MINI[0];
            int rowNumber = AnimationConstants.HELICOPTER_MINI[1] / AnimationConstants.HELICOPTER_MINI[0];
            double spriteWidth = vehicleImage.getWidth() / columnNumber;
            double spriteHeight = vehicleImage.getHeight() / rowNumber;
            ImageView vehicleView = new ImageView(vehicleImage);
            double helicopterWidth = 50;
            vehicleView.setFitWidth(helicopterWidth);
            vehicleView.setViewport(new Rectangle2D(0, 0, spriteWidth, spriteHeight));
            vehicleView.setPreserveRatio(true);
            vehicleView.setScaleX(-1);
            vehicleView.setOpacity(1);
            root.getChildren().add(vehicleView);
            vehicleView.relocate(MainStage.getInstance().getWidth() - width, 0);
            Animation animation = new SpriteAnimation(vehicleView,
                    Duration.millis(durationOfAnimation),columnNumber, rowNumber * columnNumber,
                    0, 0, (int) spriteWidth, (int) spriteHeight);
            animation.setCycleCount((int) (duration / 2 / durationOfAnimation));

            TranslateTransition translateTransitionGo = new TranslateTransition(Duration.millis(duration / 2), vehicleView);
            translateTransitionGo.setByX(width - helicopterWidth);
            translateTransitionGo.setByY(0);

            TranslateTransition translateTransitionReturn = new TranslateTransition(Duration.millis(duration / 2), vehicleView);
            translateTransitionReturn.setByX(helicopterWidth - width);
            translateTransitionReturn.setByY(0);

            translateTransitionGo.setOnFinished(event -> {
                vehicleView.setScaleX(1);
                translateTransitionReturn.play();
                animation.play();
            });

            translateTransitionReturn.setOnFinished(event -> root.getChildren().remove(vehicleView));

            animation.play();
            translateTransitionGo.play();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private HBox createPriceLabel(int value) throws FileNotFoundException {
        HBox box = new HBox();
        box.setSpacing(5);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: Ivory; -fx-border-color: Gray; -fx-border-radius: 2px; -fx-border-style: solid outside; -fx-border-width: 2px");
        Label moneyLabel = new Label(Integer.toString(value));
        moneyLabel.setStyle("-fx-font-family: 'Spicy Rice'; -fx-font-size: 8px;");
        Image coin = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                "coin.png").toString()));
        ImageView coinView = new ImageView(coin);
        coinView.setFitWidth(8);
        coinView.setPreserveRatio(true);
        box.getChildren().addAll(moneyLabel, coinView);
        return box;
    }
}
