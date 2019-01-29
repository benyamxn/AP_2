package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Farm;
import model.Well;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class WellGUI  implements Hoverable, Pausable{
    private int duration = 1000;
    private Well well;
    private Image image;
    private ImageView imageView;
    private int width;
    private int height;
    private int columns = AnimationConstants.WELL[0];
    private int count = AnimationConstants.WELL[1];
    private int frameWidth;
    private int cycleCount = 8;
    private SpriteAnimation animation;


    public void refill() {
        FarmGUI.getSoundPlayer().playTrack("splash");
        animation = new SpriteAnimation(imageView, Duration.millis(duration), count, columns,
                0, 0,width, height);
        animation.setCycleCount(cycleCount);
        animation.setOnFinished(event -> {
            well.setRemainingWater(well.getCapacity());
            imageView.setImage(image);
            animation = null;
        });
        animation.setRate(DurationManager.getRate());
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
            setMouseEvent(imageView);
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
            imageView.setImage(image);
            width = (int) image.getWidth() / columns;
            height = (int) image.getHeight() / columns;
            imageView.setViewport(new Rectangle2D(0, 0,width,height));
            imageView.setFitWidth(frameWidth);
            imageView.setPreserveRatio(true);
            imageView.setOpacity(1);
            setMouseEvent(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        imageView.setOnMouseClicked(eventHandler);
    }

    @Override
    public void setRate(double rate) {
        if(rate == DurationManager.pauseRate ){
            pause();
            return;
        }
        else  if(rate == DurationManager.resumeRate){
            resume();
            return;
        }
        if(animation != null)
            animation.setRate(rate);
    }

    @Override
    public void pause() {
        if(animation != null )
            animation.pause();
    }

    @Override
    public void resume() {
        if(animation != null )
            animation.play();
    }

    public Well getWell() {
        return well;
    }
}
