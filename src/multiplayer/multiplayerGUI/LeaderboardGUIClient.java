package multiplayer.multiplayerGUI;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import multiplayer.ClientSenderThread;
import multiplayer.Player;
import multiplayer.multiplayerModel.messages.LeaderboardRequestMessage;

import java.util.ArrayList;

public class LeaderboardGUIClient {
    private static boolean visible = false;
    private double width, height;
    private AnchorPane pane;
    private LeaderboardTable leaderboardTable;
    // TODO: toggle visible on click.
    private ClientSenderThread senderThread = ClientSenderThread.getInstance();
    private Pane possiblePane = new AnchorPane();

    public LeaderboardGUIClient(double width, double height) {
        pane = new AnchorPane();
        this.width = width;
        this.height = height;
        initTable(new ArrayList<>());
    }

    public void initTable(ArrayList<Player> players) {
        leaderboardTable = new LeaderboardTable(players, width, height, false);
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

    public static boolean isVisible() {
        return visible;
    }

    public static void setVisible(boolean visible) {
        LeaderboardGUIClient.visible = visible;
    }

    public void useVBox(VBox vBox) {
        vBox.getChildren().add(pane);
        vBox.setSpacing(10);
        vBox.setMargin(pane, new Insets(10, 10, 10, 10));
    }

    public Pane getPossibleVBox() {
        return possiblePane;
    }

    public void setPossiblePane(Pane possiblePane) {
        this.possiblePane = possiblePane;
    }
}
