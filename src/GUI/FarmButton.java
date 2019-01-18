package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class FarmButton {

    private int radius;
    private Circle circle;
    private Rectangle countRenderer;
    private Label priceLabel;

    public FarmButton(int radius) {
        this.radius = radius;
        circle = new Circle(radius);
        countRenderer = new Rectangle(2 * radius, radius / 2.0, Color.GOLD);
    }

    public void addToRoot(Pane parent) {
        parent.getChildren().addAll(circle, countRenderer, priceLabel);
    }

    public void relocateInRoot(double x, double y) {
        circle.relocate(x, y);
        countRenderer.relocate(x, y + 2 * radius);
        priceLabel.relocate(x, y + 2 * radius);
    }

    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        circle.setOnMouseClicked(eventHandler);
        countRenderer.setOnMouseClicked(eventHandler);
        priceLabel.setOnMouseClicked(eventHandler);
        // TODO: is the line above necessary?
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setPriceLabel(Label priceLabel) {
        this.priceLabel = priceLabel;
    }
}
