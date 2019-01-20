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
    private Image image;
    private ImageView imageView;
    private AnchorPane pane = new AnchorPane();

    public WarehouseGUI(Warehouse warehouse) {
        this.warehouse = warehouse;
        initImage();
        setImageView();
        pane.getChildren().add(imageView);

        setMouseEvent(pane);
    }

    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        imageView.setOnMouseClicked(eventHandler);
    }

    public void upgrade() {
        initImage();
        setImageView();
        pane = new AnchorPane();
        pane.getChildren().add(imageView);
    }

    private void setImageView() {
        imageView = new ImageView(image);
        imageView.setFitWidth(0.14 * MainStage.getInstance().getWidth());
        imageView.setFitHeight(0.14 * MainStage.getInstance().getHeight());
        imageView.setPreserveRatio(true);

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
        double scaleX = 0.9, scaleY = 0.9;
        System.out.println("Fit Height:\t" + imageView.getFitHeight());
        double width = (imageView.getFitWidth() * scaleX) / (8 + 2 * (level - 1));
        double height = (imageView.getFitHeight() * scaleY) / (8 + 2 * (level - 1));
        System.out.println("Width, height:\t" + width + " and " + height);
        int productIndex = 0;
        for (ProductType product : warehouse.getContents()) {
            try {
                //TODO: there's no such product as "feather"
                Image productImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                        "Products", product.toString().replace(" ", "") + ".png").toString()));
                ImageView productImageView = new ImageView(productImage);
                productImageView.setFitWidth(width);
                productImageView.setFitHeight(height);
                productImageView.setPreserveRatio(true);
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
