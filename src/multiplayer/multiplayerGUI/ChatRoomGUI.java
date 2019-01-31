package multiplayer.multiplayerGUI;

import GUI.Hoverable;
import GUI.MainStage;
import controller.Main;
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
import javafx.scene.shape.Rectangle;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.messages.ChatMessage;

import java.io.IOException;

public class ChatRoomGUI {

    private VBox vBox;
    private ChatRoom chatRoom;
    private TextArea textArea = new TextArea();
    private ListView messages = new ListView<Node>();
    private Label replyTo = new Label();
    private Label replyText = new Label();

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
        Rectangle rectangle  = new Rectangle();

        HBox.setMargin(textArea,new Insets(0,10,0,0));
        HBox.setMargin(send,new Insets(0,0,0,100));
        textArea.setPrefHeight(hbox.getPrefHeight());
        textArea.setId("messageArea");
        replyTo.setId("replyTo");
        hbox.getChildren().addAll(textArea,send);
        VBox.setMargin(messages,new Insets(10,0,0,0));
        VBox.setMargin(replyTo, new Insets(20,50,0,0));
        VBox.setMargin(replyText, new Insets(0,50,10,0));
        VBox.setMargin(send,new Insets(10,0,0,0));
        vBox.getChildren().addAll(messages,replyTo,replyText,hbox);
        messages.setEditable(false);
        setSendButton(send);
    }

    public void setSendButton(Button send){
        send.setOnMouseClicked(event -> {
            if(! textArea.getText().equals("")){
                MainStage.getInstance().getSoundUI().playTrack("click");
//                chatRoom.sendMessage(null,text.getText());
                addMessage(new ChatMessage(new CompactProfile("salm","s"),textArea.getText()));
                textArea.setText("");

            } else MainStage.getInstance().getSoundUI().playTrack("error");
        });
        Hoverable.setMouseHandler(send);
    }

    public void addMessage(ChatMessage chatMessage){

        Label name = new Label(chatMessage.getSender().getName() + " : \n");
        name.setId("senderName");
        name.setTextFill(Color.BLUE);
        Label text = new Label(chatMessage.getText());
        text.setId("messageText");
        text.setOnMouseClicked(event -> {
            if(event.getClickCount() >= 2){
                replyTo.setText(name.getText());
                replyText.setText(text.getText());
                replyTo.setVisible(true);
                replyText.setVisible(true);
            }
        });
        messages.getItems().add(name);
        messages.getItems().add(text);
    }

}
