package multiplayer.multiplayerModel.messages;

public class ProfileRequestMessage extends Message {
    String id;

    public ProfileRequestMessage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
