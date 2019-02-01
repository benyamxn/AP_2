package multiplayer.multiplayerModel.messages;

import multiplayer.Player;

public class FriendAcceptRequest extends Message {

    private Player newfriend;

    public void setNewfriend(Player newfriend) {
        this.newfriend = newfriend;
    }

    public Player getNewfriend() {
        return newfriend;
    }
}
