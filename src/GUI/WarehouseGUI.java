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
    private static final double startX = 52.0 / 180, startY = 56.0 / 148, endX = 133.0 / 180, endY = 100.0 / 148;

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
        updatePane();
    }

    private void updatePane() {
        pane = new AnchorPane(imageView);
    }

    private void setImageView() {
        imageView = new ImageView(image);
        imageView.setFitWidth(0.16 * MainStage.getInstance().getWidth());
        imageView.setFitHeight(0.16 * MainStage.getInstance().getHeight());
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
//        updatePane();
        // TODO: products are not removed after they're sold
        pane.getChildren().remove(1, pane.getChildren().size());
        // TODO: products are visually not correctly placed inside of the warehouse
        int level = warehouse.getLevel();
        double scaleX = endX - startX, scaleY = endY - startY;
        double width = (imageView.getFitWidth() * scaleX) / (8 + 2 * (level - 1));
        double height = (imageView.getFitHeight() * scaleY) / (8 + 2 * (level - 1));
        int productIndex = 0;
        for (ProductType product : warehouse.getContents()) {
            try {
                Image productImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                        "Products", product.toString().replace(" ", "") + ".png").toString()));
                ImageView productImageView = new ImageView(productImage);
                productImageView.setFitWidth(width);
                productImageView.setFitHeight(height);
                productImageView.setPreserveRatio(true);
                productImageView.relocate((startX + productIndex % level) * width,  (1.0 * productIndex / level + startY) * height);
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
