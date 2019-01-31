package multiplayer.multiplayerGUI;

import GUI.Hoverable;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.messages.ChatMessage;

import java.io.IOException;

public class ChatRoomGUI {

    private VBox vBox;
    private ChatRoom chatRoom;
    private TextArea textArea = new TextArea();
    private ListView messages = new ListView<Node>();

    public ChatRoomGUI(VBox vBox) {
        this.vBox = vBox;
    }

    public ChatRoomGUI(VBox vbox, ChatRoom chatRoom) {
        this.vBox = vbox;
        this.chatRoom = chatRoom;
        chatRoom.setChatRoomGUI(this);
    }

    public void init(){
        Button send = new Button("send");
        HBox hbox = new HBox();
        HBox.setMargin(textArea,new Insets(0,10,0,0));
        HBox.setMargin(send,new Insets(0,0,0,100));
        textArea.setPrefHeight(hbox.getPrefHeight());
        hbox.getChildren().addAll(textArea,send);
        VBox.setMargin(messages,new Insets(10,0,0,0));
        VBox.setMargin(send,new Insets(10,0,0,0));
        vBox.getChildren().addAll(messages,hbox);
        messages.setEditable(false);
        setSendButton(send);
    }

    public void setSendButton(Button send){
        send.setOnMouseClicked(event -> {
            if(! textArea.getText().equals("")){
//                chatRoom.sendMessage(null,text.getText());
                addMessage(new ChatMessage(new CompactProfile("salm","s"),textArea.getText()));
                textArea.setText("");

            }
        });
        Hoverable.setMouseHandler(send);
    }

    public void addMessage(ChatMessage chatMessage){

        Label name = new Label(chatMessage.getSender().getName() + " : \n");
        name.setTextFill(Color.BLUE);
        Label text = new Label(chatMessage.getText());
        text.setOnMouseClicked(event -> {
            if(event.getClickCount() >= 2){
                textArea.setText("");
                textArea.setText("replying TO");
            }
        });
        messages.getItems().add(name);
        messages.getItems().add(text);
    }

}
