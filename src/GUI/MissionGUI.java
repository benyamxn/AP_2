package GUI;

import javafx.geometry.Point2D;
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
import java.util.EnumMap;
import java.util.Map;

public class MissionGUI {
    private Group root = new Group();
    private ImageView imageView;
    private Image image;
    private Image doneImage;
    private double doneX;
    double width = MainStage.getInstance().getWidth() / 7;
    double height;
    private Game game;
    private double startX, startY;
    private Label[] labels = new Label[4];
    private int[] counts = new int[3];
    private int counter = 0;
    private Point2D scenePoint;

    public MissionGUI(Game game) {
        this.game = game;
        Label infoLabel = new Label("Goals");
        infoLabel.setStyle("-fx-font-family: 'Spicy Rice'; -fx-font-size: 20px");
        Label infoLabel2 = new Label("Current (Target)");
        infoLabel2.setStyle("-fx-font-family: 'Spicy Rice'; -fx-font-size: 20px");
        try {
            doneImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                    "done.png").toString()));
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","backgrounds",
                    "mission.png").toString()));
            height = image.getHeight() * width / image.getWidth();
            imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setPreserveRatio(true);
            root.getChildren().add(imageView);
            startX = MainStage.getInstance().getWidth() - width;
            startY = MainStage.getInstance().getHeight() - height;
            doneX = startX + width * 0.47;
            scenePoint = new Point2D(startX, startY);
            imageView.relocate(startX, startY);
            counter = 0;
            infoLabel.relocate(startX + 0.4 * width, startY + 0.065 * height);
            infoLabel2.relocate(startX + 0.25 * width, startY + 0.8 * height);
            root.getChildren().addAll(infoLabel, infoLabel2);
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
                labels[counter].setStyle("-fx-font-family: 'Spicy Rice'; -fx-font-size: 18px");
                root.getChildren().add(labels[counter]);
                productView.relocate(startX + width * 0.11764, startY + height * 0.286 + height * 0.125 * counter);
                labels[counter].relocate(startX + width * 0.24, startY + height * 0.28 + height * 0.125 * counter + 5);
                counter++;
            }
            {
                ImageView coinView = new ImageView(new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                        "coin.png").toString())));
                coinView.setFitWidth(width * 0.09);
                coinView.setPreserveRatio(true);
                root.getChildren().add(coinView);
                counts[counter] = game.getMission().getMoneyGoal();
                labels[counter] = new Label("From " + counts[counter] + " you have " + game.getMoney());
                labels[counter].setStyle("-fx-font-family: 'Spicy Rice'; -fx-font-size: 18px");
                root.getChildren().add(labels[counter]);
                coinView.relocate(startX + width * 0.11764, startY + height * 0.286 + height * 0.125 * counter);
                labels[counter].relocate(startX + width * 0.24, startY + height * 0.28 + height * 0.125 * counter + 5);
            }
            updateMoney();
            updateProducts();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void addToRoot(Pane pane) {
        pane.getChildren().add(root);
    }

    public void updateMoney() {
        labels[counter].setText(game.getMoney() + " (" + counts[counter] + ")");
        if (game.getMoney() >= counts[counter])
            addDoneLabel(counter);
    }

    public void updateProducts() {
        EnumMap<ProductType, Integer> productMap = game.getFarm().getWarehouse().getProductMap();
        int i = 0;
        for (Map.Entry<ProductType, Integer> entry : game.getMission().getProductsGoal().entrySet()) {
            if (i == 3)
                break;
            Integer number = productMap.get(entry.getKey());
            if (number == null)
                number = 0;
            labels[i].setText(number + " (" + counts[i] + ")");
            if (number >= counts[i]) {
                addDoneLabel(i);
            }
            i++;
        }
    }

    private void addDoneLabel(int index) {
        ImageView doneImageView = new ImageView(doneImage);
        doneImageView.relocate(doneX, startY + height * 0.125 * index + 5);
        doneImageView.setScaleX(0.35);
        doneImageView.setScaleY(0.35);
        doneImageView.setPreserveRatio(true);
        root.getChildren().add(doneImageView);
    }
}
