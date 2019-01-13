package GUI;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Animal;
import model.Direction;
import model.Domesticated;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AnimalGUI {
    private Image[] image = new Image[7];
    private Animal animal;


    public AnimalGUI(Animal animal) {
        this.animal = animal;
        Path cur = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(cur.toString(), "res", "animals", animal.toString());
        image[0] = new Image(Paths.get(filePath.toString(), "up.png").toString());
        image[1] = new Image(Paths.get(filePath.toString(), "up_left.png").toString());
        image[2] = new Image(Paths.get(filePath.toString(), "left.png").toString());
        image[3] = new Image(Paths.get(filePath.toString(), "down_left.png").toString());
        image[4] = new Image(Paths.get(filePath.toString(), "down.png").toString());
        if (new File(Paths.get(filePath.toString(), "eat.png").toString()).exists()) {
            image[5] = new Image(Paths.get(filePath.toString(), "eat.png").toString());
        }
        if (new File(Paths.get(filePath.toString(), "death.png").toString()).exists()) {
            image[6] = new Image(Paths.get(filePath.toString(), "death.png").toString());
        }
    }

    private ImageView createImageView(){
        Direction dir = animal.getDirection();
        Boolean rotate = false;
        int index = -1;
        switch (dir){
            case LEFT:
                index = 2;
                break;
            case RIGHT:
                rotate = true;
                break;
            case UP:
                index = 0;
                break;
            case DOWN:
                break;
            case UP_RIGHT:
                rotate = true;
                break;
            case UP_LEFT:
                index = 1;
                break;
            case DOWN_RIGHT:
                rotate = true;
                break;
            case DOWN_LEFT:
                index = 3;
                break;
            case STATIONARY:
                if (animal.getHealth() > 0)
                    index = 5;
                else
                    index = 6;
                break;
        }
        ImageView imageView = new ImageView(image[index]);
        if (rotate)
            imageView.setScaleY(-1);
        return imageView;
    }

    public void render(){
        ImageView imageView = createImageView();
        double height = imageView.getImage().getHeight();
        double width = imageView.getImage().getWidth();
    }

}
