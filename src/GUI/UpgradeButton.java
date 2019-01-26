package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Upgradable;

public class UpgradeButton implements Hoverable{
    private Upgradable upgradable;
    private Button button;

    public UpgradeButton(Upgradable upgradable) {
        this.upgradable = upgradable;
        button = new Button(Integer.toString(upgradable.getUpgradePrice()));
        if (!upgradable.canUpgrade()) {
            button.setVisible(false);
            button.setDisable(true);
        }
        button.getStyleClass().add("upgradeBtn");
        setMouseEvent(button);
    }

    public void addToRoot(Pane pane){
        pane.getChildren().add(button);
    }

    public void relocate(double x, double y) {
        button.relocate(x, y);
    }

    public void render() {
        if (!upgradable.canUpgrade()) {
            button.setVisible(false);
            button.setDisable(true);
            return;
        }
        FarmGUI.getSoundPlayer().playTrack("upgrade");
        button.setText(Integer.toString(upgradable.getUpgradePrice()));
    }

    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) {
        button.setOnMouseClicked(eventHandler);
    }

}
