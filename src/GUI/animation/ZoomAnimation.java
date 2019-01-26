package GUI.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.util.Duration;

public class ZoomAnimation {

    private static int MAX_ZOOM_LEVEL = 10;
    private static double DRAG_FACTOR = 3;

    private Timeline timeline;

    public ZoomAnimation() {
        this.timeline = new Timeline(60);
    }

    public void zoom(Node node, double factor, double x, double y, Rectangle2D screenBounds) {
        double oldScale = node.getScaleX();
        double scale = oldScale * factor;
        if (scale <= 1) {
            scale = 1;
        } else if (scale >= 16) {
            scale = 16;
        }
        double f = (scale / oldScale) - 1;
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
        double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));
        Rectangle2D bounds2 = new Rectangle2D(
                bounds.getMinX() + bounds.getWidth() * (-f) / 2 - f * dx,
                bounds.getMinY() + bounds.getHeight() * (-f) / 2 - f * dy,
                bounds.getWidth() * factor,
                bounds.getHeight() * factor
        );
        double fixX = 0;
        double fixY = 0;
        if (bounds2.getMinX() > screenBounds.getMinX()) {
            fixX = screenBounds.getMinX() - bounds2.getMinX();
        }
        if (bounds2.getMaxX() <= screenBounds.getMaxX()) {
            fixX = screenBounds.getMaxX() - bounds2.getMaxX();
        }
        if (bounds2.getMinY() > screenBounds.getMinY()) {
            fixY = screenBounds.getMinY() - bounds2.getMinY();
        }
        if (bounds2.getMaxY() <= screenBounds.getMaxY()) {
            fixY = screenBounds.getMaxY() - bounds2.getMaxY();
        }
        timeline.getKeyFrames().clear();
        if (scale > 1) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), node.getTranslateX() - f * dx + fixX)),
                    new KeyFrame(Duration.millis(200), new KeyValue(node.translateYProperty(), node.getTranslateY() - f * dy + fixY))
            );
        } else {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), 0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(node.translateYProperty(), 0))
            );
        }

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(node.scaleXProperty(), scale)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.scaleYProperty(), scale))
        );
        timeline.play();
    }


    public void drag(Node node, double deltaX, double deltaY, Rectangle2D screenBounds) {

        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        Rectangle2D bounds2 = new Rectangle2D(
                bounds.getMinX() + deltaX * DRAG_FACTOR,
                bounds.getMinY() + deltaY * DRAG_FACTOR,
                bounds.getWidth(),
                bounds.getHeight()
        );
        double fixX = 0;
        double fixY = 0;
        if (bounds2.getMinX() > screenBounds.getMinX()) {
            fixX = screenBounds.getMinX() - bounds2.getMinX();
        }
        if (bounds2.getMaxX() <= screenBounds.getMaxX()) {
            fixX = screenBounds.getMaxX() - bounds2.getMaxX();
        }
        if (bounds2.getMinY() > screenBounds.getMinY()) {
            fixY = screenBounds.getMinY() - bounds2.getMinY();
        }
        if (bounds2.getMaxY() <= screenBounds.getMaxY()) {
            fixY = screenBounds.getMaxY() - bounds2.getMaxY();
        }
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(20), new KeyValue(node.translateXProperty(), node.getTranslateX() + deltaX * DRAG_FACTOR + fixX)),
                new KeyFrame(Duration.millis(20), new KeyValue(node.translateYProperty(), node.getTranslateY() + deltaY * DRAG_FACTOR + fixY))
        );
        timeline.play();
    }
}

