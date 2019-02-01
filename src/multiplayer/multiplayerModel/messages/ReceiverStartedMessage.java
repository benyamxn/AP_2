package multiplayer.multiplayerModel.messages;

import multiplayer.multiplayerModel.CompactProfile;

public class ReceiverStartedMessage extends Message {
    public ReceiverStartedMessage() {
    }

    public ReceiverStartedMessage(CompactProfile sender) {
        super(sender);
    }
}
