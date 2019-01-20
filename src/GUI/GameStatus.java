package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class GameStatus {
    private int money;
    private int turns;
    private VBox rectangle;
    private Label moneyLabel;
    private Label turnsLabel;

    public GameStatus(int money, int turns) {
        this.money = money;
        this.turns = turns;
        rectangle = new VBox();
        moneyLabel = new Label();
        turnsLabel = new Label();
        updateMoneyLabel();
        updateTurnsLabel();
        rectangle.setSpacing(10);
//        rectangle.setMargin(moneyLabel, new Insets(10, 10, 10, 10));
//        rectangle.setMargin(turnsLabel, new Insets(10, 10, 10, 10));
        try {
            rectangle.getChildren().addAll(createMoneyBox(), turnsLabel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        rectangle.setSpacing(10);
        rectangle.setPadding(new Insets(10, 10, 10, 10));
        rectangle.setAlignment(Pos.CENTER);
        rectangle.setStyle("-fx-background-color: linear-gradient(to bottom left, #0f10ff, #6a5acd); " +
                "-fx-border-radius: 3px; -fx-border-style: solid outside; -fx-border-width: 1px; -fx-border-color: #0f10ff");
        styleLabels();
    }

    public GameStatus(int money) {
        this(money, 0);
    }

    public void setMoney(int money) {
        this.money = money;
        updateMoneyLabel();
    }

    public void setTurns(int turns) {
        this.turns = turns;
        updateTurnsLabel();
    }

    private void updateTurnsLabel() {
        turnsLabel.setText("Turns: " + turns);
    }

    private void updateMoneyLabel() {
        moneyLabel.setText("Money: "+ money);
    }

    public void addToRoot(Pane root) {
        root.getChildren().addAll(rectangle);
    }

    public void relocate(double x, double y) {
        rectangle.relocate(x, y);
    }

    public int getTurns() {
        return turns;
    }

    private HBox createMoneyBox() throws FileNotFoundException {
        HBox box = new HBox();
        Image coin = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                "coin.png").toString()));
        ImageView coinView = new ImageView(coin);
        coinView.setPreserveRatio(true);
        coinView.setFitWidth(20);
        box.getChildren().addAll(moneyLabel, coinView);
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void styleLabels() {
        String style = "-fx-font-family: 'Spicy Rice'; -fx-font-size: 20; -fx-text-fill: Ivory";
        moneyLabel.setStyle(style);
        turnsLabel.setStyle(style);
    }
}
