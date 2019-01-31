package multiplayer.multiplayerGUI;

import GUI.Hoverable;
import GUI.MainStage;
import controller.Main;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.messages.ChatMessage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class ChatRoomGUI {

    private VBox vBox;
    private ChatRoom chatRoom;
    private TextArea textArea = new TextArea();
    private ListView messages = new ListView<Node>();
    private Label replyTo = new Label();
    private Label replyText = new Label();

    private ImageView close;

    public ChatRoomGUI(VBox vBox) {
        this.vBox = vBox;

        try {
            close = new ImageView(new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","close.png").toString())));
            close.setVisible(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ChatRoomGUI(VBox vbox, ChatRoom chatRoom) {
        this(vbox);
        this.chatRoom = chatRoom;
        chatRoom.setChatRoomGUI(this);
    }

    public void init(){
        Button send = new Button("send");
        HBox hbox = new HBox();
        replyTo.setId("mainMenu");
        textArea.setPrefHeight(hbox.getPrefHeight());
        textArea.setId("messageArea");
        replyTo.setId("replyTo");
        close.setOnMouseClicked(event -> {
            replyText.setText("");
            replyTo.setText("");
            close.setVisible(false);
        });
        VBox.setMargin(messages,new Insets(10,0,0,0));
        VBox.setMargin(close,new Insets(5,5,0,0));
        VBox.setMargin(replyTo, new Insets(0,20,0,0));
        VBox.setMargin(replyText, new Insets(0,20,10,0));
        VBox.setMargin(send,new Insets(10,0,0,0));
        VBox messageFormat = new VBox();
        messageFormat.getChildren().addAll(close,replyTo,replyText);
        HBox.setMargin(messageFormat,new Insets(10,10,0,0));

        textArea.setPrefRowCount(1);
        HBox.setMargin(textArea,new Insets(0,10,10,10));
        HBox.setMargin(send,new Insets(0,0,0,20));

        hbox.getChildren().addAll(messageFormat,textArea,send);

        vBox.getChildren().addAll(messages,hbox);
        messages.setEditable(false);
        setSendButton(send);
    }

    public void setSendButton(Button send){
        send.setOnMouseClicked(event -> {
            if(! textArea.getText().equals("")){
                MainStage.getInstance().getSoundUI().playTrack("click");
                if(close.isVisible()){
                    chatRoom.sendMessage(replyTo.getText() + "\n" + replyText.getText(),textArea.getText());
                }else {
                    chatRoom.sendMessage(null,textArea.getText());
                }
                textArea.setText("");

            } else MainStage.getInstance().getSoundUI().playTrack("error");
        });
        Hoverable.setMouseHandler(send);
    }

    public void addMessage(ChatMessage chatMessage){

        Label reply = new Label(chatMessage.getReplyingTO());
        Label name = new Label(chatMessage.getSender().getName()+ " :    " + reply.getText());
        name.setId("senderName");
        name.setTextFill(Color.BLUE);
        Label text = new Label(chatMessage.getText());
        text.setId("messageText");
        text.setOnMouseClicked(event -> {
            if(event.getClickCount() >= 2){
                replyTo.setText(chatMessage.getSender().getName());
                replyText.setText(text.getText());
                close.setVisible(true);
            }
        });
        messages.getItems().add(name);
        messages.getItems().add(text);
    }

}
