package multiplayer.multiplayerModel.messages;

import multiplayer.Player;

import java.util.ArrayList;
import java.util.List;

public class OnlineUserRequest extends Message {

    List<Player> players;

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
