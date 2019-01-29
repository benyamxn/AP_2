package multiplayer;

import multiplayer.messages.Message;

import java.io.ObjectOutputStream;

public class Packet {
    private Message message;
    private ObjectOutputStream objectOutputStream;

    public Packet(Message message, ObjectOutputStream objectOutputStream) {
        this.message = message;
        this.objectOutputStream = objectOutputStream;
    }

    public Message getMessage() {
        return message;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}
