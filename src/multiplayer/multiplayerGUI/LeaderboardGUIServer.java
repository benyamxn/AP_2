package multiplayer.multiplayerGUI;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import multiplayer.Player;
import multiplayer.ServerSenderThread;

import java.util.ArrayList;

public class LeaderboardGUIServer {
    private double width, height;
    private AnchorPane pane;
    private LeaderboardTable leaderboardTable;
    private ServerSenderThread senderThread = ServerSenderThread.getInstance();

    public LeaderboardGUIServer(double width, double height) {
        pane = new AnchorPane();
        this.width = width;
        this.height = height;
    }

    public void initTable(ArrayList<Player> players) {
        leaderboardTable = new LeaderboardTable(players, width, height);
        leaderboardTable.addToRoot(pane);
        leaderboardTable.relocate(0,0);
    }

    public LeaderboardTable getLeaderboardTable() {
        return leaderboardTable;
    }

    public void addToRoot(Pane root) {
        root.getChildren().add(pane);
    }

    public void relocate(double x, double y) {
        leaderboardTable.relocate(x, y);
    }
}
