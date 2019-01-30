package multiplayer.multiplayerModel.messages;

import multiplayer.multiplayerModel.*;
import java.io.Serializable;

public abstract class Message implements Serializable {
    private CompactProfile sender;
    private CompactProfile receiver;

    public CompactProfile getSender() {
        return sender;
    }

    public void setSender(CompactProfile sender) {
        this.sender = sender;
    }

    public CompactProfile getReceiver() {
        return receiver;
    }

    public void setReceiver(CompactProfile receiver) {
        this.receiver = receiver;
    }

    public Message(CompactProfile sender) {
        this.sender = sender;
    }

    public Message(CompactProfile sender, CompactProfile receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
}
