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
    private ImageView imageView = new ImageView();
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
        imageView.setImage(image);
        setImageView();
    }

    private void setImageView() {
        imageView.setImage(image);
        imageView.setFitWidth(0.20 * MainStage.getInstance().getWidth());
        imageView.setFitHeight(0.20 * MainStage.getInstance().getHeight());
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
        int row, column;
//        row = column = 8 + 2 * (level - 1);
        row = column = 6;
        double offsetX, offsetY;
        offsetX = imageView.getFitWidth() * startX;
        offsetY = imageView.getFitHeight() * startY;
        double width = (imageView.getFitWidth() * scaleX) / row;
        double height = (imageView.getFitHeight() * scaleY) / column;
        int productIndex = 0;
        for (ProductType product : warehouse.getContents()) {
            try {
                Image productImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                        "Products", product.toString().replace(" ", "") + ".png").toString()));
                ImageView productImageView = new ImageView(productImage);
                productImageView.setFitWidth(width);
                productImageView.setFitHeight(height);
                productImageView.setPreserveRatio(true);
                productImageView.setScaleY(2.8);
                productImageView.setScaleX(2.8);
                productImageView.relocate(width * (double) (productIndex % row) + offsetX / 2,  height * ((double) (productIndex / column)) + offsetY);
                pane.getChildren().add(productImageView);
                productIndex++;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void relocate(double x, double y) {
        pane.relocate(x, y);
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().add(this.pane);
    }
}
