package multiplayer.multiplayerModel.messages;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SendPreviousMessagesRequest extends Message {

    List<ChatMessage> messages;

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
