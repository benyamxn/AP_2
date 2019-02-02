package multiplayer.multiplayerGUI;

import GUI.MainStage;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import multiplayer.Packet;
import multiplayer.Player;
import multiplayer.ServerSenderThread;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.Shop;
import multiplayer.multiplayerModel.messages.OnlineUserRequest;
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
    public   ChatGUI chatGUI;
    private OnlineUserPage onlineUserPage = new OnlineUserPage();
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
        chatGUI = new ChatGUI();
        AnchorPane chatPane = new AnchorPane();
        chatPane.setBottomAnchor(vBox,100.0);
        chatPane.setTopAnchor(vBox, 0.0);
        chatPane.setRightAnchor(vBox, 100.0);
        chatPane.setLeftAnchor(vBox, 0.0);
        chatPane.getChildren().add(vBox);
        chatGUI.init(vBox);
        chatPane.relocate(width /2 , height * 0.1);
        pane.getChildren().add(chatPane);
        for (ChatRoom chatRoom : server.getChatRooms()) {
            new ChatRoomGUI(chatRoom);
            chatGUI.addChat(chatRoom);
        }

        onlineUserPage.setOnMouse(event -> {
            onlineUserPage.getvBox().getChildren().clear();
            chatGUI.init(onlineUserPage.getvBox());
        });
        chatGUI.setNewMessage(event -> {
            chatGUI.getvBox().getChildren().clear();
            update(server.getPlayers());
            onlineUserPage.init(chatGUI.getvBox());
        });


    }


    private void createShopGUI(){
        shopGUI = new ShopGUI(server.getShop(),width * 0.4 , height * 0.25);
        server.getShop().setShopGUI(shopGUI);
        shopGUI.addToPane(pane);
        shopGUI.relocate(width * 0.5 , height * 0.6 );
    }

    public LeaderboardGUIServer getLeaderboardGUIServer() {
        return leaderboardGUIServer;
    }



    public ShopGUI getShopGUI() {
        return shopGUI;
    }

    public AnchorPane getPane() {
        return pane;
    }


    public void update(ArrayList<Player> players){
        ListView list = onlineUserPage.getList();
        Platform.runLater(() -> {
            list.getItems().clear();
        });
        for (Player player : players) {
            Label label = new Label(player.getId());
            label.setOnMouseClicked(event -> {
                if(event.getClickCount() >= 2) {
                    ChatRoom chatRoom;
                    if ((chatRoom = server.getChatRoomByReceiver(new CompactProfile(player.getName(), player.getId()))) == null) {
                        chatRoom = new ChatRoom(false, new CompactProfile(player.getName(), player.getId()));
                        server.getChatRooms().add(chatRoom);
                        ChatRoomGUI chatRoomGUI = new ChatRoomGUI(chatRoom);
                        chatGUI.addChat(chatRoom);
                    }
                    onlineUserPage.getvBox().getChildren().clear();
                    chatRoom.getChatRoomGUI().init(onlineUserPage.getvBox());
                }
            });

            Platform.runLater(() -> {
                onlineUserPage.getList().getItems().add(label);
            });
        }
    }
}
