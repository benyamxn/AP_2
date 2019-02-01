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

    private VBox vBox;

    public OnlineUserPage() {

    }

    public void setOnMouse(EventHandler<? super MouseEvent> event){
        back.setOnMouseClicked(event);
    }

    public void init(VBox vBox){
        this.vBox = vBox;
        vBox.getChildren().addAll(back,list);
    }


    public ListView getList() {
        return list;
    }

    public VBox getvBox() {
        return vBox;
    }
}
