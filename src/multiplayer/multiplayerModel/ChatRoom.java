package multiplayer.multiplayerModel;



import multiplayer.multiplayerModel.messages.ChatMessage;

import java.util.LinkedList;

public class ChatRoom {

    private LinkedList<ChatMessage> chatMessages = new LinkedList<>();
    public LinkedList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void addMessage(ChatMessage message){
        chatMessages.add(message);
    }


    public void reply(CompactProfile replyTo,ChatMessage message){


    }
}
