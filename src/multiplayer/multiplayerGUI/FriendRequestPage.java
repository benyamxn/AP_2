package multiplayer.multiplayerGUI;

import GUI.Hoverable;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import multiplayer.ClientSenderThread;
import multiplayer.client.Client;
import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.messages.FriendAcceptRequest;
import multiplayer.multiplayerModel.messages.FriendRequest;
import multiplayer.multiplayerModel.messages.Message;

import java.util.List;

public class FriendRequestPage {

    private ListView list = new ListView<Node>();
    private CompactProfile myProfile;
    private VBox vBox;

    public FriendRequestPage(CompactProfile myProfile) {
        this.myProfile = myProfile;
        list.setId("text");
    }

    public void init(VBox vBox) {
        this.vBox = vBox;
        vBox.getChildren().add(list);
    }

    public void addRequest(CompactProfile compactProfile){
        HBox hBox = new HBox();
        Button accept = new Button("Accept");
        Button decline = new Button("Decline");
        decline.setOnMouseClicked(event -> {
            list.getItems().remove(hBox);
            list.refresh();
        });
        accept.setOnMouseClicked(event -> {
            Message message = new FriendAcceptRequest();
            message.setReceiver(compactProfile);
            message.setSender(myProfile);
            ClientSenderThread.getInstance().addToQueue(message);
            list.getItems().remove(hBox);
            list.refresh();
        });
        Label name  = new Label(compactProfile.getId());
        hBox.getChildren().addAll(name,accept,decline);
        Platform.runLater(()->{
            list.getItems().add(hBox);
        });
        Hoverable.setMouseHandler(accept);
        Hoverable.setMouseHandler(decline);
    }

}
