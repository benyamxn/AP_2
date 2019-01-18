package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
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

public class AnimalGUI {

    private Image[] image = new Image[7];
    private Animal animal;
    private ImageView imageView;
    private static double DURATION = 1000;
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
            constants[1] = AnimationConstants.getConstants(animal.toString() + " up");
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
               image[5] = image[0];
            }

            if (new File(Paths.get(filePath.toString(), "death.png").toString()).exists()) {
                image[6] = new Image(new FileInputStream(Paths.get(filePath.toString(), "death.png").toString()));
                constants[6] = AnimationConstants.getConstants(animal.toString() + " death");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView = createImageView();
        int temp = (int) Math.ceil(1.0 * constants[imageIndex][1] / constants[imageIndex][0]);
        System.out.println(imageIndex);
        double height =  imageView.getImage().getHeight() / temp;
        imageView.setViewport(new Rectangle2D(0,0,image[imageIndex].getWidth() / constants[imageIndex][0],
                height));
        System.out.println(constants[imageIndex][0] + ":" + height);
////
//        imageView.setViewport(new Rectangle2D(0,0,500,500));
    }

    private ImageView createImageView(){
        Direction dir = animal.getDirection();
        boolean rotate = false;
        switch (dir){
            case LEFT:
                imageIndex = 2;
                break;
            case RIGHT:
                rotate = true;
                break;
            case UP:
                imageIndex = 0;
                break;
            case DOWN:
                break;
            case UP_RIGHT:
                rotate = true;
                break;
            case UP_LEFT:
                imageIndex = 1;
                break;
            case DOWN_RIGHT:
                rotate = true;
                break;
            case DOWN_LEFT:
                imageIndex = 3;
                break;
            case STATIONARY:
                if (animal.getHealth() > 0) {
                    imageIndex = 5;
                    if(animal instanceof Cat || animal instanceof Dog)
                        imageIndex = 0;
                }
                else
                    imageIndex = 6;
                break;
        }
        ImageView imageView = new ImageView(image[imageIndex]);
        if (rotate)
            imageView.setScaleY(-1);
        return imageView;
    }

    public void render(){
        imageView = createImageView();
        double height = imageView.getImage().getHeight();
        double width = imageView.getImage().getWidth();
    }

    public void move (){
        double difWidth = FarmGUI.cellWidth;
        double difHeight = FarmGUI.cellHeight;
        int width = 0;
        try {
            width = (int) imageView.getImage().getWidth() / constants[imageIndex][0];
        } catch (ArithmeticException e) {
            e.printStackTrace();
            System.out.println(imageIndex);
            System.out.println("Here is the error");
        }
        System.out.println(imageIndex);
        int temp = (int) Math.ceil(1.0 * constants[imageIndex][1] / constants[imageIndex][0]);
        int height = (int) imageView.getImage().getHeight() / temp;
        Animation animation = new SpriteAnimation(imageView, Duration.millis(DURATION), constants[imageIndex][1], constants[imageIndex][0], 0, 0, width, height);
        animation.play();
        double[] lineTo = FarmGUI.getPointForCell(animal.getLocation().getWidth(), animal.getLocation().getHeight());
        System.out.println(animal.getDirection());
        Point moveVector = animal.getDirection().getMoveVector();
        double[] moveTo = {lineTo[0] - moveVector.getWidth() * difWidth ,  lineTo[1] - moveVector.getHeight() * difHeight };
        System.out.println( moveVector.getWidth() + " --- " + moveVector.getHeight());
        System.out.println("Coordinates: " +moveTo[0] +":" + moveTo[1] +"---" + lineTo[0] + ":" + lineTo[1]);
        javafx.scene.shape.Path path = new javafx.scene.shape.Path(new MoveTo(moveTo[0], moveTo[1]), new LineTo(lineTo[0], lineTo[1]));
//        PathTransition pathTransition = new PathTransition(Duration.millis(DURATION * WALK), path, imageView);
        PathTransition pathTransition = new PathTransition(Duration.millis(1000), path, imageView);
        pathTransition.play();
    }

    public ImageView getImageView() {
        return imageView;
    }
}
