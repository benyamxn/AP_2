package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class FarmCityView {
    private AnchorPane root;
    private Image image;
    private ImageView imageView;
    private Game game;
    private double width;
    private double factor = 100;
    private double durationOfAnimation = 100;


    public FarmCityView(Game game, double width) {
        root = new AnchorPane();
        this.game = game;
        this.width = width;

        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","backgrounds",
                    "cityFarm.jpg").toString()));
            imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(event -> {
                runTruck();
                runHelicopter();
            });
            root.getChildren().add(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void relocate(double x, double y) {
        imageView.relocate(x, y);
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().add(root);
    }

    public void runTruck() {
        Image vehicleImage;
        double vehicleHeight, imageHeight;
        imageHeight = image.getHeight() * (width / image.getWidth());
        System.out.println(imageHeight);
        double duration = game.getFarm().getTruck().getArrivalTime() * factor;
        try {
            vehicleImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures","UI",
                    "Truck", "0" + game.getFarm().getTruck().getLevel() + "_mini.png").toString()));
            double spriteWidth = vehicleImage.getWidth() / AnimationConstants.TRUCK_MINI[0];
            double spriteHeight = vehicleImage.getHeight() / (AnimationConstants.TRUCK_MINI[1] / AnimationConstants.TRUCK_MINI[0]);
            ImageView vehicleView = new ImageView(vehicleImage);
            double truckWidth = 50;
            vehicleView.setFitWidth(truckWidth);
            vehicleHeight = vehicleImage.getHeight() * (truckWidth * (AnimationConstants.TRUCK_MINI[0] * AnimationConstants.TRUCK_MINI[0] / AnimationConstants.TRUCK_MINI[1]) / vehicleImage.getWidth());
            vehicleView.setPreserveRatio(true);
            vehicleView.setViewport(new Rectangle2D(0, 0, spriteWidth, spriteHeight));
            vehicleView.setScaleX(-1);
            vehicleView.setOpacity(1);
            root.getChildren().add(vehicleView);
            vehicleView.relocate(MainStage.getInstance().getWidth() - width, imageHeight - vehicleHeight);
            Animation animation = new SpriteAnimation(vehicleView,
                    Duration.millis(durationOfAnimation), AnimationConstants.TRUCK_MINI[1],
                    AnimationConstants.TRUCK_MINI[0], 0, 0, (int) spriteWidth, (int) spriteHeight);
            animation.setCycleCount((int) (duration / 2 / durationOfAnimation));

            TranslateTransition translateTransitionGo = new TranslateTransition(Duration.millis(duration / 2), vehicleView);
            translateTransitionGo.setByX(width - truckWidth);
            translateTransitionGo.setByY(0);

            TranslateTransition translateTransitionReturn = new TranslateTransition(Duration.millis(duration / 2), vehicleView);
            translateTransitionReturn.setByX(truckWidth - width);
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

    public void runHelicopter() {
        Image vehicleImage;
        double duration = game.getFarm().getHelicopter().getArrivalTime() * factor;
        try {
            vehicleImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures","UI",
                    "Helicopter", "0" + game.getFarm().getHelicopter().getLevel() + "_mini.png").toString()));
            double spriteWidth = vehicleImage.getWidth() / AnimationConstants.HELICOPTER_MINI[0];
            double spriteHeight = vehicleImage.getHeight() / (AnimationConstants.HELICOPTER_MINI[1] / AnimationConstants.HELICOPTER_MINI[0]);
            ImageView vehicleView = new ImageView(vehicleImage);
            double helicopterWidth = 50;
            vehicleView.setFitWidth(helicopterWidth);
            vehicleView.setPreserveRatio(true);
            vehicleView.setViewport(new Rectangle2D(0, 0, spriteWidth, spriteHeight));
            vehicleView.setScaleX(-1);
            vehicleView.setOpacity(1);
            root.getChildren().add(vehicleView);
            vehicleView.relocate(MainStage.getInstance().getWidth() - width, 0);
            Animation animation = new SpriteAnimation(vehicleView,
                    Duration.millis(durationOfAnimation), AnimationConstants.HELICOPTER_MINI[1],
                    AnimationConstants.HELICOPTER_MINI[0], 0, 0, (int) spriteWidth, (int) spriteHeight);
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
}
