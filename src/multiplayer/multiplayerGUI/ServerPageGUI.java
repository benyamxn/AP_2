package multiplayer.multiplayerGUI;

import GUI.MainStage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
        createChatGUI();
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
        VBox vBox = new VBox();
        chatRoomGUI = new ChatRoomGUI(vBox,server.getChatRoomByReceiver(null));
        AnchorPane chatPane = new AnchorPane();
        chatPane.setBottomAnchor(vBox,0.0);
        chatPane.setTopAnchor(vBox, 0.0);
        chatPane.setRightAnchor(vBox, 0.0);
        chatPane.setLeftAnchor(vBox, 0.0);
        chatPane.getChildren().add(vBox);

        chatRoomGUI.init();
        pane.getChildren().add(chatPane);


    }


}
