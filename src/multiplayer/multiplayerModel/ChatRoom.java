package multiplayer.multiplayerModel;

import multiplayer.ClientSenderThread;
import multiplayer.Packet;
import multiplayer.ServerSenderThread;
import multiplayer.multiplayerGUI.ChatRoomGUI;
import multiplayer.multiplayerModel.messages.ChatMessage;
import multiplayer.server.Server;

import java.util.LinkedList;

public class ChatRoom {

    private boolean isClient;
    private ChatRoomGUI chatRoomGUI;
    private LinkedList<ChatMessage> chatMessages = new LinkedList<>();


    public ChatRoom(boolean isClient) {
        this.isClient = isClient;
    }

    public LinkedList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void addMessage(ChatMessage message){
        chatMessages.add(message);
        chatRoomGUI.addMessage(message);
    }

    public void sendMessage(CompactProfile replyTo,String text){
        ChatMessage message = new ChatMessage(text);
        message.setReplyingTO(replyTo);
        if(isClient) {
            ClientSenderThread.getInstance().addToQueue(message);
        } else {
            ServerSenderThread.getInstance().addToQueue(new Packet(message,null));
        }
    }
    
}
