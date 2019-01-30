package multiplayer.multiplayerGUI;

import GUI.MainStage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import multiplayer.server.Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ServerPageGUI {
    private Server server;
    private double width = MainStage.getInstance().getWidth();
    private double height = MainStage.getInstance().getHeight();
    private LeaderboardGUIServer leaderboardGUIServer;
    private AnchorPane pane = new AnchorPane();
    private ChatRoomGUI chatRoomGUI;
    public ServerPageGUI(Server server) {
        this.server = server;
        loadBackground();
        createLeaderboard();

        MainStage.getInstance().pushStack(pane);
    }




    private void loadBackground() {
        Image image = null;
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","backgrounds",
                    "doubledMenu.png").toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        pane.getChildren().add(imageView);
    }

    private void createLeaderboard() {
        leaderboardGUIServer = new LeaderboardGUIServer(width * 0.4, height * 0.8);
        leaderboardGUIServer.addToRoot(pane);
        leaderboardGUIServer.initTable(new ArrayList<>());
        leaderboardGUIServer.relocate(20, 0.1 * height);
    }

    private void createChatGUI() {

    }
}
