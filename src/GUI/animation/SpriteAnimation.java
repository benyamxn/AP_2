package GUI.animation;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {


    private final ImageView imageView;
    private int count;
    private int columns;
    private int offsetX;
    private int offsetY;
    private int width;
    private int height;

    private Rectangle2D lastViewPort;
    private int lastIndex;

    public SpriteAnimation(
            ImageView imageView,
            Duration duration,
            int count, int columns,
            int offsetX, int offsetY,
            int width, int height) {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.lastViewPort = imageView.getViewport();
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            imageView.setOpacity(1);
            lastIndex = index;
        }
    }


    @Override
    public void stop() {
        super.stop();
        imageView.setViewport(lastViewPort);
        imageView.setOpacity(1);
    }


    public void setOffset(int offsetX, int offsetY) {
        stop();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }


    public void setCountAndColumns(int count) {
        stop();
        this.count = count;
        this.columns = count;
    }


    public void setDuration(Duration duration) {
        stop();
        setCycleDuration(duration);
    }


    public void setSize(int height, int width) {
        stop();
        this.height = height;
        this.width = width;
    }
}
