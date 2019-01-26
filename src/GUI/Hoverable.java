package GUI;

import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;


public interface Hoverable {

    default void setMouseEvent(Node node){
        setMouseHandler(node);
    }

    static void setMouseHandler(Node node) {
        ColorAdjust colorAdjust = new ColorAdjust();
        node.setOnMouseEntered(event -> {
            MainStage.getInstance().getSoundUI().playTrack("hover in");
            colorAdjust.setBrightness(0.5);
            node.setEffect(colorAdjust);
        });
        node.setOnMouseExited(event -> {
            MainStage.getInstance().getSoundUI().playTrack("hover out");
            colorAdjust.setBrightness(0);
            node.setEffect(colorAdjust);
        });
    }
}
