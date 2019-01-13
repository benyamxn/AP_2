package GUI;

import javafx.scene.image.Image;
import model.Animal;

public class AnimalGUI {
    private Image image;
    private Animal animal;

    public AnimalGUI(Animal animal) {
        this.animal = animal;
        image = new Image("res/animal/" + animal.toString() +  + ".jpeg"); // TODO check this
    }


    move()

}
