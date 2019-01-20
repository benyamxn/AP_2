package GUI;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.ProductType;
import model.Warehouse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class WarehouseGUI implements Hoverable {

    private Warehouse warehouse;
    private final boolean sale = false;
    private Image image;
    private ImageView imageView;
    private AnchorPane pane = new AnchorPane();

    public WarehouseGUI(Warehouse warehouse) {
        this.warehouse = warehouse;
        initImage();
        imageView = new ImageView(image);
        pane.getChildren().add(imageView);

        setMouseEvent(pane);
    }

    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        imageView.setOnMouseClicked(eventHandler);
    }

    public void upgrade() {
        initImage();
    }

    private void initImage() {
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"), "res", "Textures", "Service", "Depot",
                    "0" + warehouse.getLevel() + ".png").toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        int level = warehouse.getLevel();
        double offsetX = 5, offsetY = 5;
        double width = (image.getWidth() - offsetX) / (8 + 2 * (level - 1));
        double height = (image.getHeight() - offsetY) / (8 + 2 * (level - 1));
        int productIndex = 0;
        for (ProductType product : warehouse.getProductList()) {
            try {
                //TODO: there's no such product as "feather"
                Image productImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                        "Products", product.toString().replace(" ", ""), "normal.png").toString()));
                ImageView productImageView = new ImageView(productImage);
                productImageView.setFitWidth(width);
                //TODO: check the relocating
                productImageView.relocate((productIndex % level) * width, (1.0 * productIndex / level) * height);
                pane.getChildren().add(productImageView);
                productIndex++;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void relocate(double x, double y) {
        pane.relocate(x, y);
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().add(this.pane);
    }
}
