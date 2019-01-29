package multiplayer.messages;

import multiplayer.Player;

import java.util.ArrayList;

public class LeaderboardStat extends Message {
    private ArrayList<Player> playersStatus;

    public LeaderboardStat(String sender, String receiver, ArrayList<Player> playersStatus) {
        super(sender, receiver);
        this.playersStatus = playersStatus;
    }

    public ArrayList<Player> getPlayersStatus() {
        return playersStatus;
    }
}
