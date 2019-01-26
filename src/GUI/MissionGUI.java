package GUI;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Game;
import model.Product;
import model.ProductType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Map;

public class MissionGUI {
    private Group root = new Group();
    private ImageView imageView;
    private Image image;
    double width = MainStage.getInstance().getWidth() / 8;
    double height;
    private Game game;
    private double startX, startY;
    private Label[] labels = new Label[3];
    private int[] counts = new int[3];

    public MissionGUI(Game game) {
        this.game = game;
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","backgrounds",
                    "mission.png").toString()));
            height = image.getHeight() * width / image.getWidth();
            imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setPreserveRatio(true);
            root.getChildren().add(imageView);
            startX = MainStage.getInstance().getWidth() - width;
            startY = MainStage.getInstance().getHeight() - height;
            imageView.relocate(startX, startY);
            int counter = 0;
            for (Map.Entry<ProductType, Integer> entry : game.getMission().getProductsGoal().entrySet()) {
                if (counter == 3)
                    break;
                ProductGUI productGUI = new ProductGUI(new Product(entry.getKey()), 1);
                ImageView productView = productGUI.getImageView();
                productView.setFitWidth(width * 0.114);
                productView.setPreserveRatio(true);
                root.getChildren().add(productView);
                counts[counter] = entry.getValue();
                labels[counter] = new Label("From " + counts[counter] + " you have 0");
                labels[counter].setStyle("-fx-font-family: 'Spicy Rice'; -fx-font-size: 8px");
                root.getChildren().add(labels[counter]);
                productView.relocate(startX + width * 0.11764, startY + height * 0.286 + height * 0.12 * counter);
                labels[counter].relocate(startX + width * 0.24, startY + height * 0.286 + height * 0.12 * counter + 5);
                counter++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void addToRoot(Pane pane) {
        pane.getChildren().add(root);

    }
}
