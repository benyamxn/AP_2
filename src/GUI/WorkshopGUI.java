package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Warehouse;
import model.Workshop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;


public class WorkshopGUI {

    public static final int DURATION = 1000;
    private Workshop workshop;
    private Image image;
    private boolean rotate;
    private int width ;
    private int height;
    private int columns = AnimationConstants.WORKSHOP[0];
    private ImageView imageView;
    public WorkshopGUI(Workshop workshop , boolean rotate) throws FileNotFoundException {
        this.workshop = workshop;
        image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures","Workshops",
                workshop.getType().toString() ,"0" + workshop.getLevel() + ".png").toString()));
        imageView = new ImageView(image);
        width = (int) image.getWidth() / columns;
        height = (int) image.getHeight() / columns;
        imageView.setViewport(new Rectangle2D(0, 0,width,height));
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        imageView.setOpacity(1);
        this.rotate = rotate;
        if(rotate == true){
            imageView.setScaleX(-1);
        }
    }

    public void Produce(){
        final Animation animation = new SpriteAnimation(imageView, Duration.millis(DURATION), AnimationConstants.WORKSHOP[1], columns,
                0, 0, width, height);
        animation.play();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void upgrade(){
        image = new Image(Paths.get(System.getProperty("user.dir"),"res","Textures","Workshops",
                workshop.getType().toString() +"0" + workshop.getLevel() + ".png").toString());
        imageView.setImage(image);
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
        if(rotate == true){
            imageView.setScaleX(-1);
        }
    }
}
