package GUI;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameStatus {
    private int money;
    private int turns = 0;
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
        rectangle.setMargin(moneyLabel, new Insets(10, 10, 10, 10));
        rectangle.setMargin(turnsLabel, new Insets(10, 10, 10, 10));
        rectangle.getChildren().addAll(moneyLabel, turnsLabel);
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
}
