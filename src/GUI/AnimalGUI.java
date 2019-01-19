package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.util.Duration;
import model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class AnimalGUI {

    private Image[] image = new Image[7];
    private Animal animal;
    private ImageView imageView  = new ImageView();
    private static double DURATION = 1900;
    private static final int WALK = 2;
    private int[][] constants = new int[7][2];
    private int imageIndex = -1;

    public AnimalGUI(Animal animal)  {
        this.animal = animal;
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
                if(new File(Paths.get(filePath.toString(), "caged.png").toString()).exists()){
                    image[5] = new Image(new FileInputStream(Paths.get(filePath.toString(), "caged.png").toString()));
                    image[5] = image[0];
                }
                else {
                    image[5] = image[0];
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
                    if(animal instanceof Cat || animal instanceof Dog || animal instanceof Wild)
                        imageIndex = 0;
                }
                else
                    imageIndex = 6;
                break;
        }
        imageView.setImage(image[imageIndex]);
        if (rotate)
            imageView.setScaleX(-1);
        return imageView;
    }

    public void render(){
        imageView = setImageView();
        double height = imageView.getImage().getHeight();
        double width = imageView.getImage().getWidth();
    }

    public void move (){
        if(animal.getDirection().equals(Direction.STATIONARY))
            return;
        if(animal instanceof Cat){
            animal.toString();
        }
        setImageView();
        double difWidth = FarmGUI.cellWidth;
        double difHeight = FarmGUI.cellHeight;
        int[] size  = getSizeOfFrame();
        Animation animation = new SpriteAnimation(imageView, Duration.millis(DURATION), constants[imageIndex][1], constants[imageIndex][0], 0, 0,size[0], size[1]);
        animation.play();
        Point moveVector = animal.getDirection().getMoveVector();
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(DURATION),imageView);
        translateTransition.setByX(moveVector.getWidth() * difWidth);
        translateTransition.setByY(-1 * moveVector.getHeight() * difHeight);
        translateTransition.play();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void relocate(double x, double y){
        imageView.relocate(x,y);
    }

    public void dead(){
        if(animal instanceof Dog || animal instanceof Wild){
            animal = null;
            FarmGUI.anchorPane.getChildren().remove(imageView);
            return;
        }
        imageIndex = 6;
        imageView.setImage(image[imageIndex]);
        int[] size = getSizeOfFrame();
        Animation animation = new SpriteAnimation(imageView, Duration.millis(DURATION), constants[imageIndex][1], constants[imageIndex][0], 0, 0,size[0], size[1]);
        animation.play();
        animation.setOnFinished(event ->{
            animal = null;
            FarmGUI.anchorPane.getChildren().remove(imageView);
        });
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }


    public void eat(){
        imageIndex = 5;
        imageView.setImage(image[imageIndex]);
        int[] size = getSizeOfFrame();
        Animation animation = new SpriteAnimation(imageView, Duration.millis(DURATION), constants[imageIndex][1], constants[imageIndex][0], 0, 0,size[0], size[1]);
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
}
