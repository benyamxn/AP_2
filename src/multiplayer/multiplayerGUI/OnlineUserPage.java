package multiplayer.multiplayerGUI;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import multiplayer.Player;

import java.util.ArrayList;

public class OnlineUserPage {

    private ListView list = new ListView<Node>();
    private Button back = new Button("Back");
    private EventHandler<? super MouseEvent> event;


    public OnlineUserPage() {

    }

    public void setEvent(EventHandler<? super MouseEvent> event) {
        this.event = event;
    }

    public void init(VBox vBox){

        vBox.getChildren().addAll(back,list);
    }



    public void update(ArrayList<Player> players){

        list.getItems().clear();
        for (Player player : players) {
            Label label = new Label(player.getId());
            label.setOnMouseClicked(event);
        }
    }


}
