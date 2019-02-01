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
    private CompactProfile receiver;

    public ChatRoom(boolean isClient) {
        this.isClient = isClient;
    }

    public ChatRoom(boolean isClient, CompactProfile receiver) {
        this.isClient = isClient;
        this.receiver = receiver;
    }

    public void setChatRoomGUI(ChatRoomGUI chatRoomGUI) {
        this.chatRoomGUI = chatRoomGUI;
    }

    public LinkedList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void addMessage(ChatMessage message){
        chatMessages.add(message);
        chatRoomGUI.addMessage(message);
    }

    public void sendMessage(String replyTo,String text){

        ChatMessage message = new ChatMessage(text);
        message.setReplyingTO(replyTo);
        message.setGlobal(receiver == null);
        message.setReceiver(receiver);
        if(isClient) {
            synchronized (ClientSenderThread.getInstance().getQueue()) {
                ClientSenderThread.getInstance().addToQueue(message);
            }

        } else {
            message.setSender(new CompactProfile("HOST","HOST"));
            addMessage(message);
            ServerSenderThread.getInstance().addToQueue(new Packet(message,null));
        }
    }

    public CompactProfile getReceiver() {
        return receiver;
    }

}
