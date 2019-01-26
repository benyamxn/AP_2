package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
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

public class AnimalBuyButton extends FarmButton implements Hoverable{
    private Animal animal;
    private Button button;

    public AnimalBuyButton(int radius, Animal animal) {
        super(radius);
        this.animal = animal;
        try {
            Image image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                    "AnimalBuyButtons",animal.toString()+ ".png").toString()));
            getCircle().setFill(new ImagePattern(image));
            setPriceLabel(new Label(Integer.toString(animal.getBuyPrice())));
            button = new Button(Integer.toString(animal.getBuyPrice()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void relocateInRoot(double x, double y) {
        getCircle().relocate(x, y);
        button.relocate(x + getRadius() - 40, y + 2 * getRadius() + 3);
        button.setOnMouseClicked(getCircle().getOnMouseClicked());
        button.getStyleClass().add("buyBtn");
    }

    @Override
    public void addToRoot(Pane parent) {
        parent.getChildren().addAll(getCircle(), button);
    }

    @Override
    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        getCircle().setOnMouseClicked(eventHandler);
        button.setOnMouseClicked(eventHandler);
    }

    public Animal getAnimal() {
        return animal;
    }
}
