package GUI;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import model.ProductType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class ProductButton extends FarmButton{

    private ProductType productType;
    private Label countLabel;
    private VBox countRenderer;
    private int count = 0;

    public ProductButton(int radius, ProductType productType, boolean sale) {
        super(radius);
        this.productType = productType;
        try {
            Image image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                    "Products", productType.toString(), "normal.png").toString()));
            getCircle().setFill(new ImagePattern(image));
            if (sale) {
                // for warehouse
                setPriceLabel(new Label(Integer.toString(productType.getSaleCost())));
                countLabel = new Label("x " + count);
                countRenderer = new VBox();
                countRenderer.setSpacing(10);
                countRenderer.setMargin(countLabel, new Insets(10, 10, 10, 10));
                countRenderer.getChildren().addAll(countLabel);
            }
            else
                // for truck, helicopter
                setPriceLabel(new Label(Integer.toString(productType.getBuyCost())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setCount(int count) {
        this.count = count;
        updateCountLabel();
    }

    public void updateCountLabel(){
        countLabel.setText("x " + count);
    }

    public ProductType getProductType() {
        return productType;
    }
}
