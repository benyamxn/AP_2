package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Well;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class WellGUI {
    private int duration;
    private Well well;
    private Image image;
    private ImageView imageView;
    private int width;
    private int height;
    private int columns = AnimationConstants.WELL[0];
    private int count = AnimationConstants.WELL[1];
    private int frameWidth;


    public void refill() {
        Animation animation = new SpriteAnimation(imageView, Duration.millis(duration), count, columns,
                0, 0,width, height);
        animation.play();
    }

    public WellGUI(Well well, int frameWidth) {
        this.well = well;
        this.frameWidth = frameWidth;
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                    "Service", "Well", "0" + well.getLevel() + ".png").toString()));
            imageView = new ImageView(image);
            width = (int) image.getWidth() / columns;
            height = (int) image.getHeight() / columns;
            imageView.setViewport(new Rectangle2D(0, 0,width,height));
            imageView.setFitWidth(frameWidth);
            imageView.setPreserveRatio(true);
            imageView.setOpacity(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void relocate(double x, double y) {
        imageView.relocate(x, y);
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().add(imageView);
    }

    public void upgrade() {
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                    "Service", "Well", "0" + well.getLevel() + ".png").toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
