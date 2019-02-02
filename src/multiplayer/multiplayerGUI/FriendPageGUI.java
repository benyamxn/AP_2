package multiplayer.multiplayerGUI;

import GUI.Hoverable;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import multiplayer.Player;

public class FriendPageGUI {

    private VBox vBox;
    private ListView listView = new ListView<Node>();

    public FriendPageGUI() {
        listView.setId("text");
    }

    public void init(VBox vBox){
        listView.setId("text");
        this.vBox = vBox ;
        vBox.getChildren().add(listView);
    }

    public void addItem(Label label){
        Platform.runLater(() -> {
            listView.getItems().add(label);
            Hoverable.setMouseHandler(label);
        });
    }
}
