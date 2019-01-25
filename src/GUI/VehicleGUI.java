package GUI;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Vehicle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class VehicleGUI  implements Hoverable{
    private Vehicle vehicle;
    private Image image;
    private ImageView imageView;
    private int frameWidth;

    public VehicleGUI(Vehicle vehicle, int frameWidth) {
        this.vehicle = vehicle;
        this.frameWidth = frameWidth;
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                    "Service", vehicle.getVehicleType().toString(), "0" + vehicle.getLevel() + ".png").toString()));
            imageView = new ImageView(image);
            imageView.setFitWidth(frameWidth);
            imageView.setPreserveRatio(true);
            imageView.setOpacity(1);
            setMouseEvent(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void relocate(double x, double y) {
        imageView.relocate(x, y);
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().add(imageView);
    }

    public void upgrade() {
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                    "Service", vehicle.getVehicleType().toString(), "0" + vehicle.getLevel() + ".png").toString()));
            imageView.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        imageView.setOnMouseClicked(eventHandler);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
