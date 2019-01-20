package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Product;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class ProductGUI {
    private Image image;
    private Product product;
    private ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public ProductGUI(Product product, double scale) {
        this.product = product;
        product.setProductGUI(this);
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures"
                    ,"Products", product.getType().toString().replace(" ", "") + ".png").toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ;
        imageView = new ImageView(image);
        imageView.setFitHeight(FarmGUI.cellHeight);
        imageView.setPreserveRatio(true);
        imageView.setOpacity(1);
        // TODO: remove dead products in farm updating function
    }
    public Product getProduct() {
        return product;
    }

}
