package multiplayer.multiplayerModel.messages;

import multiplayer.Player;
import multiplayer.multiplayerModel.*;

import java.util.ArrayList;

public class LeaderboardStat extends Message {
    private ArrayList<Player> playersStatus;

    public LeaderboardStat(CompactProfile sender, CompactProfile receiver, ArrayList<Player> playersStatus) {
        super(sender, receiver);
        this.playersStatus = playersStatus;
    }

    public LeaderboardStat(ArrayList<Player> playersStatus) {
        this.playersStatus = playersStatus;
    }

    public ArrayList<Player> getPlayersStatus() {
        return playersStatus;
    }
}
