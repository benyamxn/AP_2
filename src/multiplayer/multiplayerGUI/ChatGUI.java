package multiplayer.multiplayerGUI;

import GUI.Hoverable;
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
    private Button newMessage = new Button("New");
    private VBox vBox;
    public ChatGUI() {
        messages.setEditable(false);
        messages.setId("text");
        Hoverable.setMouseHandler(newMessage);
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
            label.setText(chatRoom.getReceiver().getId());
        else
            label.setText("Global Chat");
        label.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                MainStage.getInstance().getSoundUI().playTrack("click");
                vBox.getChildren().clear();
                chatRoom.getChatRoomGUI().init(vBox);
            }
        });
        chatRoom.getChatRoomGUI().setOnMouseBack(event1 -> {
            MainStage.getInstance().getSoundUI().playTrack("click");
            chatRoom.getChatRoomGUI().getvBox().getChildren().clear();
            init(chatRoom.getChatRoomGUI().getvBox());
        });

        Hoverable.setMouseHandler(label);

        Platform.runLater(() -> {
            System.out.println("added ....."+ label.getText() + "size " + String.valueOf(messages.getItems().size()));
            messages.getItems().add(label);
        });

    }

    public VBox getvBox() {
        return vBox;
    }

    public ListView getMessages() {
        return messages;
    }
}
