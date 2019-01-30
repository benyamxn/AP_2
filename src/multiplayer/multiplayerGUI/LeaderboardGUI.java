package multiplayer.multiplayerGUI;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import multiplayer.ClientSenderThread;
import multiplayer.Player;
import multiplayer.messages.LeaderboardRequestMessage;

import java.util.ArrayList;

public class LeaderboardGUI {
    private static boolean visible = false;
    private double width, height;
    private AnchorPane pane;
    private LeaderboardTable leaderboardTable;
    // TODO: toggle visible on click.
    private ClientSenderThread senderThread = ClientSenderThread.getInstance();

    public LeaderboardGUI(double width, double height) {
        pane = new AnchorPane();
        senderThread.addToQueue(new LeaderboardRequestMessage());
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
        pane.relocate(x, y);
        leaderboardTable.relocate(x, y);
    }

    public static boolean isVisible() {
        return visible;
    }

    public static void setVisible(boolean visible) {
        LeaderboardGUI.visible = visible;
    }
}
