package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Workshop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;


public class WorkshopGUI implements Hoverable,Pausable {

    public static  int DURATION = 10000;
    private Workshop workshop;
    private Image image;
    private boolean rotate;
    private int width ;
    private int height;
    private int columns = AnimationConstants.WORKSHOP[0];
    private ImageView imageView;
    private Animation animation;
    public WorkshopGUI(Workshop workshop , boolean rotate , int fitWidth) throws FileNotFoundException {
        this.workshop = workshop;
        workshop.setWorkshopGUI(this);
        image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures","Workshops",
                workshop.getType().toString() ,"0" + workshop.getLevel() + ".png").toString()));
        imageView = new ImageView(image);
        width = (int) image.getWidth() / columns;
        height = (int) image.getHeight() / columns;

        imageView.setViewport(new Rectangle2D(0, 0,width,height));
        imageView.setFitWidth(fitWidth);
        imageView.setPreserveRatio(true);
        imageView.setOpacity(1);
        setMouseEvent(imageView);
        this.rotate = rotate;
        if(rotate == true){
            imageView.setScaleX(-1);
        }
    }


    public void addToRoot(Pane pane){
        pane.getChildren().add(imageView);
    }

    public void relocate(double x, double y){
        imageView.relocate(x,y);
    }


    public void produce(){
        animation = new SpriteAnimation(imageView, Duration.millis(DURATION), AnimationConstants.WORKSHOP[1], columns,
                0, 0, width, height);
        animation.play();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void upgrade(){
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures","Workshops",
                    workshop.getType().toString() ,"0" + workshop.getLevel() + ".png").toString()));
            width = (int) image.getWidth() / columns;
            height = (int) image.getHeight() / columns;
            imageView.setImage(image);
            imageView.setViewport(new Rectangle2D(0, 0, width, height));
            if(rotate == true){
                imageView.setScaleX(-1);
            }
            DURATION = workshop.getProductionTime() * 2000;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        imageView.setOnMouseClicked(eventHandler);
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    @Override
    public void setRate(double rate) {
        if(animation != null)
             animation.setRate(rate);
    }

}
