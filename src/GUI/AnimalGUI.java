package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AnimalGUI implements Pausable{

    private Image[] image = new Image[7];
    private Image cageImage;
    private Animal animal;
    private ImageView imageView  = new ImageView();
    private ImageView cageView = new ImageView();
    private int cageWidth, cageHeight;
    private double scale = 1;
    {
        imageView.setOnMouseClicked(event -> {
            if (animal instanceof Wild) {
                event.consume();
                ((Wild) animal).incrementCagedLevel();
            }
        });
        cageView.setOnMouseClicked(event -> {
            if (animal instanceof Wild) {
                event.consume();
                ((Wild) animal).incrementCagedLevel();
            }
        });
    }
    private static double DURATION = 1000;
    private static final int WALK = 2;
    private int[][] constants = new int[7][2];
    private int imageIndex = -1;
    private Animation animation;
    private TranslateTransition translateTransition, cageTranslateTransition;

    public AnimalGUI(Animal animal)  {
        this.animal = animal;
        try {
            cageImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                    "Cages", "build02.png").toString()));
            cageView.setImage(cageImage);
            cageWidth = (int) (cageImage.getWidth()) / AnimationConstants.CAGE_BUILD_2[0];
            cageHeight = (int) (cageImage.getHeight())/ 2;
            cageView.setViewport(new Rectangle2D(0, 0,cageWidth,cageHeight));
            cageView.setOpacity(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Animal getAnimal() {
        return animal;
    }

    public void initImages() {
        try {
            Path cur = Paths.get(System.getProperty("user.dir"));
            Path filePath = Paths.get(cur.toString(), "res","Textures", "Animals",animal.toString());
            image[0] = new Image(new FileInputStream(Paths.get(filePath.toString(), "up.png").toString()));
            constants[0] = AnimationConstants.getConstants(animal.toString() + " up");
            image[1] = new Image(new FileInputStream(Paths.get(filePath.toString(), "up_left.png").toString()));
            constants[1] = AnimationConstants.getConstants(animal.toString() + " up left");
            image[2] = new Image(new FileInputStream(Paths.get(filePath.toString(), "left.png").toString()));
            constants[2] = AnimationConstants.getConstants(animal.toString() + " left");
            image[3] = new Image(new FileInputStream(Paths.get(filePath.toString(), "down_left.png").toString()));
            constants[3] = AnimationConstants.getConstants(animal.toString() + " down left");
            image[4] = new Image(new FileInputStream(Paths.get(filePath.toString(), "down.png").toString()));
            constants[4] = AnimationConstants.getConstants(animal.toString() + " down");
            if (new File(Paths.get(filePath.toString(), "eat.png").toString()).exists()) {
                image[5] = new Image(new FileInputStream(Paths.get(filePath.toString(), "eat.png").toString()));
                constants[5] = AnimationConstants.getConstants(animal.toString() + " eat");
            } else {
                if (new File(Paths.get(filePath.toString(), "caged.png").toString()).exists()) {
                    image[5] = new Image(new FileInputStream(Paths.get(filePath.toString(), "caged.png").toString()));
                    constants[5] = AnimationConstants.getConstants(animal.toString() + " caged");
                } else {
                    image[5] = image[0];
                    constants[5] = constants[0];
                }
            }

            if (new File(Paths.get(filePath.toString(), "death.png").toString()).exists()) {
                image[6] = new Image(new FileInputStream(Paths.get(filePath.toString(), "death.png").toString()));
                constants[6] = AnimationConstants.getConstants(animal.toString() + " death");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setImageView();
        int temp = (int) Math.ceil(1.0 * constants[imageIndex][1] / constants[imageIndex][0]);
        double height =  imageView.getImage().getHeight() / temp;
        imageView.setViewport(new Rectangle2D(0,0,image[imageIndex].getWidth() / constants[imageIndex][0],
                height));
        setImageViewScales();
    }

    private ImageView setImageView(){
        Direction dir = animal.getDirection();
        boolean rotate = false;
        switch (dir){
            case LEFT:
                imageIndex = 2;
                break;
            case RIGHT:
                rotate = true;
                imageIndex = 2;
                break;
            case UP:
                imageIndex = 0;
                break;
            case DOWN:
                imageIndex = 4;
                break;
            case UP_RIGHT:
                rotate = true;
                imageIndex = 1;
                break;
            case UP_LEFT:
                imageIndex = 1;
                break;
            case DOWN_RIGHT:
                rotate = true;
                imageIndex = 3;
                break;
            case DOWN_LEFT:
                imageIndex = 3;
                break;
            case STATIONARY:
                if (animal.getHealth() > 0) {
                    imageIndex = 5;
                    if(animal instanceof Cat || animal instanceof Wild)
                        imageIndex = 0;
                }
                else
                    imageIndex = 6;
                break;
        }
        imageView.setImage(image[imageIndex]);
        if (rotate) {
            imageView.setScaleX(-1);
        }
        else{
            imageView.setScaleX(1);
        }
        return imageView;
    }

    private void setImageViewScales() {
        if (animal instanceof  Wild) {
            scale = 1.5;
        }
        if (animal instanceof Domesticated){
            if (((Domesticated) animal).getType().equals(DomesticatedType.OSTRICH)){
                scale = 1.2;
            }
            if (((Domesticated) animal).getType().equals(DomesticatedType.GUINEA_FOWL)){
                scale = 0.8;
            }
            if (((Domesticated) animal).getType().equals(DomesticatedType.BUFFALO)){
                scale = 1.6;
            }
        }
        if (animal instanceof Dog) {
            scale = 1.2;
        }
        if (animal instanceof Cat){
            scale = 1.02;
        }
        cageView.setFitHeight(FarmGUI.cellHeight * 2 * scale);
        cageView.setPreserveRatio(true);
        imageView.setFitHeight(scale * FarmGUI.cellHeight);
        imageView.setPreserveRatio(true);
    }

    public void move (){
        if(animal.getDirection().equals(Direction.STATIONARY))
            return;
        setImageView();
        double difWidth = FarmGUI.cellWidth;
        double difHeight = FarmGUI.cellHeight;
        int[] size  = getSizeOfFrame();
        animation = new SpriteAnimation(imageView, Duration.millis(DURATION), constants[imageIndex][1], constants[imageIndex][0], 0, 0,size[0], size[1]);
        Point moveVector = animal.getDirection().getMoveVector();
        translateTransition = new TranslateTransition(Duration.millis(DURATION),imageView);
        translateTransition.setByX(moveVector.getWidth() * difWidth);
        translateTransition.setByY( -1 *  moveVector.getHeight()  * difHeight);
        cageTranslateTransition = new TranslateTransition(Duration.millis(DURATION), cageView);
        cageTranslateTransition.setByY(-1 * moveVector.getHeight() * difHeight);
        cageTranslateTransition.setByX(moveVector.getWidth() * difWidth);
        setRate(DurationManager.getRate());
        if(!(animal instanceof Wild)){
            cageTranslateTransition = null;
        }else{
            cageTranslateTransition.play();
            cageTranslateTransition.setOnFinished(event -> cageTranslateTransition = null);
        }

        animation.play();
        translateTransition.play();
        animation.setOnFinished(event -> animation = null);
        translateTransition.setOnFinished(event -> translateTransition = null);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void dead(){
        if(animation != null & translateTransition != null){
            animation.stop();
            translateTransition.stop();
        }
        if(animal instanceof Dog || animal instanceof Wild) {
            animal = null;
            FarmGUI.anchorPane.getChildren().removeAll(imageView, cageView);
            return;
        }
        imageIndex = 6;
        imageView.setImage(image[imageIndex]);
        int[] size = getSizeOfFrame();
        animation = new SpriteAnimation(imageView, Duration.millis(DURATION * 2) , constants[imageIndex][1], constants[imageIndex][0], 0, 0,size[0], size[1]);
        setRate(DurationManager.getRate());
        animation.setOnFinished(event ->{
            animal = null;
            FarmGUI.anchorPane.getChildren().removeAll(imageView, cageView);
            animation = null;
        });
        if (animal instanceof Domesticated)
            FarmGUI.getSoundPlayer().playTrack(animal.toString() + " death");
        else
            FarmGUI.getSoundPlayer().playTrack(animal.toString());
        animation.play();
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }


    public void eat() {
        imageIndex = 5;
        imageView.setScaleX(1);
        imageView.setImage(image[imageIndex]);
        int[] size = getSizeOfFrame();
        animation = new SpriteAnimation(imageView, Duration.millis(DURATION), constants[imageIndex][1], constants[imageIndex][0], 0, 0, size[0], size[1]);
        setRate(DurationManager.getRate());
        animation.setOnFinished(event -> {
            animal.setEating(false);
            animation = null;
        });
        animation.play();
    }


    public int[] getSizeOfFrame(){
        int width = 0;
        try {
            width = (int) imageView.getImage().getWidth() / constants[imageIndex][0];
        } catch (ArithmeticException e) {
            e.printStackTrace();
            System.out.println(imageIndex);
            System.out.println("Here is the error");
        }
        int temp = (int) Math.ceil(1.0 * constants[imageIndex][1] / constants[imageIndex][0]);
        int height = (int) imageView.getImage().getHeight() / temp;

        return new int[]{width,height};
    }

    @Override
    public void setRate(double rate) {
        if(rate == DurationManager.pauseRate){
            pause();
            return;
        }
        if(rate == DurationManager.resumeRate){
            resume();
            return;
        }
        if(animation != null) {
            animation.setRate(rate);
        }
        if(translateTransition != null) {
            translateTransition.setRate(rate);
        }
        if (cageTranslateTransition != null) {
            cageTranslateTransition.setRate(rate);
        }
    }

    @Override
    public void pause() {
        if(animation != null) {
            animation.pause();
        }
        if(translateTransition != null) {
            translateTransition.pause();
        }
        if (cageTranslateTransition != null) {
            cageTranslateTransition.pause();
        }
    }

    @Override
    public void resume() {
        if(animation != null) {
            animation.play();
        }
        if(translateTransition != null) {
            translateTransition.play();
        }
        if (cageTranslateTransition != null) {
            cageTranslateTransition.play();
        }
    }

    public void cage() {
        int column = AnimationConstants.CAGE_BUILD_2[0];
        int level = ((Wild) animal).getCagedLevel();
        final int x = (level % column) * cageWidth;
        final int y = (level / column) * cageHeight;
        cageView.setViewport(new Rectangle2D( x,y,cageWidth,cageHeight));
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().addAll(imageView, cageView);
    }

    public void relocate(double x, double y) {
        imageView.relocate(x, y);
        cageView.relocate(x - FarmGUI.cellHeight / 2, y - FarmGUI.cellHeight / 2);
    }
}
