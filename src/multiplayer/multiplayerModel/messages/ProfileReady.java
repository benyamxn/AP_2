package multiplayer.multiplayerModel.messages;

import multiplayer.Player;

public class ProfileReady extends Message {
    Player player;

    public Player getPlayer() {
        return player;
    }

    public ProfileReady(Player player) {
        this.player = player;
    }
}
