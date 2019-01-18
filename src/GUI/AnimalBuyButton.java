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

public class AnimalBuyButton {
    private int radius;
    private Animal animal;
    private Circle circle;
    private Rectangle rectangle;
    private Label priceLabel;

    public AnimalBuyButton(int radius, Animal animal) {
        this.radius = radius;
        this.animal = animal;
        circle = new Circle(radius);
        rectangle = new Rectangle(2 * radius, radius / 2.0, Color.GOLD);
        try {
            Image image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                    "AnimalBuyButtons",animal.toString()+ ".png").toString()));
            circle.setFill(new ImagePattern(image));
            priceLabel = new Label(Integer.toString(animal.getBuyPrice()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addToRoot(Pane parent) {
        parent.getChildren().addAll(circle, rectangle, priceLabel);
    }

    public void relocateInRoot(double x, double y) {
        circle.relocate(x, y);
        rectangle.relocate(x, y + 2 * radius);
        priceLabel.relocate(x, y + 2 * radius);
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        circle.setOnMouseClicked(eventHandler);
        rectangle.setOnMouseClicked(eventHandler);
        priceLabel.setOnMouseClicked(eventHandler);
        // TODO: is the line above necessary?
    }
}
