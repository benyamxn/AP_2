package multiplayer.messages;

public class ChatMessage extends Message {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ChatMessage(String sender, String text) {
        super(sender);
        this.text = text;
    }

    public ChatMessage(String sender, String receiver, String text) {
        super(sender, receiver);
        this.text = text;
    }
}
