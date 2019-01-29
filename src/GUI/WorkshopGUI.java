package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Workshop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;


public class WorkshopGUI implements Hoverable,Pausable {

    private Group root = new Group();
    private static int duration = 1000;
    private static int cycleCount = 9;
    private Workshop workshop;
    private Image image;
    private boolean rotate;
    private int width ;
    private int height;
    private int columns = AnimationConstants.WORKSHOP[0];
    private ImageView imageView;
    private Animation animation;
    private ImageView infoImageView;
    private Image infoImage;
    private double infoWidth = MainStage.getInstance().getWidth() / 7;
    private double infoHeight;

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

        infoImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures","Workshops", "info.png").toString()));
        initInfoImageView();
        setMouseEvent(imageView);
        this.rotate = rotate;
        if(rotate){
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
        animation = new SpriteAnimation(imageView, Duration.millis(duration), AnimationConstants.WORKSHOP[1], columns,
                0, 0, width, height);
        animation.setCycleCount(cycleCount);
        animation.play();
        animation.setOnFinished(event -> {
            animation = null;
        });
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
            if(rotate){
                imageView.setScaleX(-1);
            }
            duration = workshop.getProductionTime();
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

    public void setOnEnter(EventHandler<? super MouseEvent> eventHandler) {
        imageView.setOnMouseEntered(eventHandler);
    }

    public void setOnExit(EventHandler<? super MouseEvent> eventHandler) {
        imageView.setOnMouseExited(eventHandler);
    }

    public void initTooltip(Tooltip tooltip) {
        Tooltip.install(imageView, tooltip);
        tooltip.setStyle("-fx-font-family: 'Spicy Rice'; -fx-font-size: 16px");
    }


    private void initInfoImageView() {
        infoImageView = new ImageView(infoImage);
        infoImageView.setScaleX(infoWidth);
        infoImageView.setScaleY(infoImage.getHeight() * infoWidth / infoImage.getWidth());
        infoImageView.setPreserveRatio(true);
    }

    @Override
    public void setRate(double rate) {
        if(rate == DurationManager.pauseRate ){
            pause();
            return;
        }
         if(rate == DurationManager.resumeRate){
            resume();
            return;
        }
         if(animation != null)
             animation.setRate(rate);
    }

    @Override
    public void pause() {
        if(animation != null)
            animation.pause();
    }

    @Override
    public void resume() {
        if(animation != null)
            animation.play();
    }

    public ImageView getInfoImageView() {
        return infoImageView;
    }
}
