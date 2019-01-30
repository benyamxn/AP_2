package multiplayer.multiplayerGUI;

import GUI.Hoverable;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerModel.messages.ChatMessage;

import java.io.IOException;

public class ChatRoomGUI {

    private VBox vBox;
    private ChatRoom chatRoom;
    private TextField text = new TextField();
    private TextArea textArea = new TextArea();

    public ChatRoomGUI(VBox vBox) {
        this.vBox = vBox;
    }

    public ChatRoomGUI(VBox vbox, ChatRoom chatRoom) {
        this.vBox = vbox;
        this.chatRoom = chatRoom;
    }

    public void init(){
        Button send = new Button("send");
        HBox hbox = new HBox();
        HBox.setMargin(text,new Insets(0,0,0,0));
        HBox.setMargin(send,new Insets(0,0,0,0));
        hbox.getChildren().addAll(text,send);
        VBox.setMargin(textArea,new Insets(10,0,0,0));
        VBox.setMargin(send,new Insets(10,20,0,30));
        vBox.getChildren().addAll(textArea,hbox);
        textArea.setEditable(false);
        setSendButton(send);
    }

    public void setSendButton(Button send){
        send.setOnMouseClicked(event -> {
            if(! text.getText().equals("")){
                chatRoom.sendMessage(null,text.getText());
                text.setText("");
            }
        });
        Hoverable.setMouseHandler(send);
    }

    public void addMessage(ChatMessage chatMessage){
        textArea.appendText(chatMessage.getSender().getName() + " : " + chatMessage.getText() + "\n");
    }

}
