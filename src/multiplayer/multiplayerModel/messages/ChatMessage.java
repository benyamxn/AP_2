package multiplayer.multiplayerModel.messages;

import multiplayer.multiplayerModel.*;

public class ChatMessage extends Message{
    private String text;
    private CompactProfile replyingTO;

    public ChatMessage(CompactProfile sender, String text) {
        super(sender);
        this.text = text;
    }

    public ChatMessage(CompactProfile sender, String text, CompactProfile replyingTO) {
        this(sender,text);
        this.replyingTO = replyingTO;
    }

    public ChatMessage(CompactProfile sender, CompactProfile receiver, String text, CompactProfile replyingTO) {
        this(sender, receiver,text);
        this.replyingTO = replyingTO;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
