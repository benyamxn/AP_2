package multiplayer.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private String sender;
    private String receiver;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


    public Message(String sender) {
        this.sender = sender;
    }

    public Message(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
}
