package multiplayer.multiplayerModel.messages;


import multiplayer.multiplayerModel.*;

public class ChatMessage extends Message{
    private String text;
    private String replyingTO;
    private boolean isGlobal  = false;

    public ChatMessage(CompactProfile sender, String text) {
        super(sender);
        this.text = text;
    }

    public ChatMessage(CompactProfile sender, String text, String replyingTO) {
        this(sender,text);
        this.replyingTO = replyingTO;
    }

    public ChatMessage(CompactProfile sender, CompactProfile receiver, String text, String replyingTO) {
        this(sender, text,replyingTO);
        super.receiver = receiver;
    }

    public ChatMessage(String text){
        super();
        this.text = text;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setReplyingTO(String replyingTO) {
        this.replyingTO = replyingTO;
    }

    public String getReplyingTO() {
        return replyingTO;
    }

    public boolean isGlobal() {
        return isGlobal;
    }
}
