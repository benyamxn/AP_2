package multiplayer.multiplayerModel.messages;

import multiplayer.Player;
import multiplayer.multiplayerModel.*;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardStat extends Message {
    private List<Player> playersStatus;

    public LeaderboardStat(List<Player> playersStatus) {
        this.playersStatus = playersStatus;
    }

    public List<Player> getPlayersStatus() {
        return playersStatus;
    }
}
