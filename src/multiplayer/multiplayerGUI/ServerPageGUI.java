package multiplayer.multiplayerGUI;

import GUI.MainStage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import multiplayer.multiplayerModel.Shop;
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
    private ShopGUI shopGUI;
    public ServerPageGUI(Server server) {
        this.server = server;
        server.setServerPageGUI(this);
        loadBackground();
        createLeaderboard();
        createChatGUI();
        createShopGUI();
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
        chatPane.setBottomAnchor(vBox,100.0);
        chatPane.setTopAnchor(vBox, 0.0);
        chatPane.setRightAnchor(vBox, 100.0);
        chatPane.setLeftAnchor(vBox, 0.0);
        chatPane.getChildren().add(vBox);
        chatRoomGUI.init();
        chatPane.relocate(width /2 , height * 0.1);
        pane.getChildren().add(chatPane);
    }

    private void createShopGUI(){
        shopGUI = new ShopGUI(server.getShop(),width * 0.4 , height * 0.25);
        shopGUI.addToPane(pane);
        shopGUI.relocate(width * 0.5 , height * 0.6 );
    }

    public LeaderboardGUIServer getLeaderboardGUIServer() {
        return leaderboardGUIServer;
    }

    public ChatRoomGUI getChatRoomGUI() {
        return chatRoomGUI;
    }

    public ShopGUI getShopGUI() {
        return shopGUI;
    }

    public AnchorPane getPane() {
        return pane;
    }
}
