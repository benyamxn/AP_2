package multiplayer.multiplayerGUI;

import GUI.MainStage;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerModel.messages.OnlineUserRequest;

import java.util.LinkedList;

public class ChatGUI {


    private ListView messages = new ListView<Node>();
    private LinkedList<ChatRoomGUI>  chatRoomGUIS;
    private Button newMessage = new Button("New");
    private VBox vBox;
    public ChatGUI() {
        messages.setId("text");
    }

    public void init(VBox vbox){
        this.vBox = vbox;
        vbox.getChildren().addAll(newMessage,messages);

    }


    public void setNewMessage(EventHandler<? super MouseEvent> event){
        newMessage.setOnMouseClicked(event);
    }

    public void addChat(ChatRoom chatRoom){

        Label label  = new Label();
        if(chatRoom.getReceiver() != null)
            label.setText(chatRoom.getReceiver().getName());
        else
            label.setText("Global Chat");
        label.setOnMouseClicked(event -> {
            if(event.getClickCount() >= 2){
                MainStage.getInstance().getSoundUI().playTrack("click");
                vBox.getChildren().clear();
                chatRoom.getChatRoomGUI().init(vBox);
                chatRoom.getChatRoomGUI().setOnMouseBack(event1 -> {
                    MainStage.getInstance().getSoundUI().playTrack("click");
                    vBox.getChildren().clear();
                    init(vBox);
                });
            }
        });
        Platform.runLater(() -> {
            messages.getItems().add(label);
        });
    }

    public VBox getvBox() {
        return vBox;
    }
}
