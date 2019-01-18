package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Animal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class AnimalBuyButton extends FarmButton{
    private Animal animal;

    public AnimalBuyButton(int radius, Animal animal) {
        super(radius);
        this.animal = animal;
        try {
            Image image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                    "AnimalBuyButtons",animal.toString()+ ".png").toString()));
            getCircle().setFill(new ImagePattern(image));
            setPriceLabel(new Label(Integer.toString(animal.getBuyPrice())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Animal getAnimal() {
        return animal;
    }
}
