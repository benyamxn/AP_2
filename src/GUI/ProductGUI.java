package GUI;

import GUI.animation.AnimationConstants;
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
            if((image = AnimationConstants.productImage.get(product.getType())) == null){
                image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures"
                        ,"Products", product.getType().toString().replace(" ", "") + ".png").toString()));
                AnimationConstants.productImage.put(product.getType(),image);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView = new ImageView(image);
        imageView.setFitHeight(FarmGUI.cellHeight);
        imageView.setPreserveRatio(true);
        imageView.setOpacity(1);
        // TODO: remove dead products in farm updating function
    }
    public Product getProduct() {
        return product;
    }

    public ImageView cagedForCell() {
        try {
            Image newImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures"
                    ,"Products", "CagedBear.png").toString()));
            ImageView newImageView = new ImageView(newImage);
            newImageView.setFitHeight(2 * FarmGUI.cellHeight);
            newImageView.setPreserveRatio(true);
            newImageView.setOpacity(1);
            return newImageView;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onTheCell(boolean onCell) {
        if (onCell) {
            imageView = cagedForCell();
        }
        else {
            imageView = new ImageView(image);
            imageView.setFitHeight(FarmGUI.cellHeight);
            imageView.setPreserveRatio(true);
            imageView.setOpacity(1);
        }
    }
}
