package multiplayer.multiplayerGUI;

import GUI.Hoverable;
import GUI.MainStage;
import controller.Controller;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Mission;
import multiplayer.ClientSenderThread;
import multiplayer.Player;
import multiplayer.client.Client;
import multiplayer.client.ClientHandler;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.messages.OnlineUserRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ClientPageGUI {
    private Client client;
    private double width = MainStage.getInstance().getWidth();
    private double height = MainStage.getInstance().getHeight();
    private LeaderboardGUIClient leaderboardGUIClient;
    private AnchorPane pane = new AnchorPane();
    private ChatGUI chatGUI = new ChatGUI();
    private FriendPageGUI friendPageGUI = new FriendPageGUI();
    private FriendRequestPage friendRequestPage ;
    private OnlineUserPage onlineUserPage = new OnlineUserPage();
    private VBox vBox = new VBox();
    private MultiplayerFarmGUI multiplayerFarmGUI ;
    public ClientPageGUI(Client client) {
        this.client = client;
        client.setClientPageGUI(this);
        loadBackground();
        createLeaderboard();
        createChatGUI();
        createButtons();
        MainStage.getInstance().pushStack(pane);
        friendRequestPage = new FriendRequestPage(client.getCompactProfile());
    }

    @SuppressWarnings("Duplicates")
    private void loadBackground() {
        Image image = null;
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","backgrounds",
                    "doubledMenu.png").toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        pane.getChildren().add(imageView);
    }

    private void createLeaderboard() {
        leaderboardGUIClient = new LeaderboardGUIClient(width * 0.4, height * 0.8);
        leaderboardGUIClient.addToRoot(pane);
        leaderboardGUIClient.relocate(20, 0.1 * height);
    }

    private void createChatGUI() {
        AnchorPane chatPane = new AnchorPane();
        chatPane.setBottomAnchor(vBox,0.0);
        chatPane.setTopAnchor(vBox, 0.0);
        chatPane.setRightAnchor(vBox, 0.0);
        chatPane.setLeftAnchor(vBox, 0.0);
        chatPane.getChildren().add(vBox);
        chatGUI.init(vBox);
        chatPane.relocate(width / 2 , height * 0.1);
        pane.getChildren().add(chatPane);
        for (ChatRoom chatRoom : client.getChatRooms()) {
            new ChatRoomGUI(chatRoom);
            chatGUI.addChat(chatRoom);
        }
        onlineUserPage.setOnMouse(event -> {
            onlineUserPage.getvBox().getChildren().clear();
            chatGUI.init(onlineUserPage.getvBox());
        });
        chatGUI.setNewMessage(event -> {
            chatGUI.getvBox().getChildren().clear();
            ClientSenderThread.getInstance().addToQueue(new OnlineUserRequest());
            onlineUserPage.init(chatGUI.getvBox());
        });
    }

    private void createButtons() {
        Button startGameButton = new Button("Start");
        Hoverable.setMouseHandler(startGameButton);
        pane.getChildren().add(startGameButton);
        startGameButton.relocate(width * 0.8, height * 0.9);
        startGameButton.setOnMouseClicked(event -> {
            Controller c = new Controller();
            c.getGame().setMission(Mission.getMissions().get(0));
            try {
                 multiplayerFarmGUI = new MultiplayerFarmGUI(c,vBox,this);
                multiplayerFarmGUI.render();
                ClientHandler.setFarmGUI(multiplayerFarmGUI);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public LeaderboardGUIClient getLeaderboardGUIClient() {
        return leaderboardGUIClient;
    }

    public AnchorPane getPane() {
        return pane;
    }


    public void update(ArrayList<Player> players){
        System.out.println(players.size());
        ListView list = onlineUserPage.getList();
        Platform.runLater(() -> {
            list.getItems().clear();
        });
        for (Player player : players) {
            Label label = new Label(player.getId());
            label.setOnMouseClicked(event -> {
                if(event.getClickCount() >= 2) {
                    ChatRoom chatRoom;
                    if ((chatRoom = client.getChatRoomByReceiver(new CompactProfile(player.getName(), player.getId()))) == null) {
                        chatRoom = new ChatRoom(true, new CompactProfile(player.getName(), player.getId()));
                        client.getChatRooms().add(chatRoom);
                        ChatRoomGUI chatRoomGUI = new ChatRoomGUI(chatRoom);
                        chatGUI.addChat(chatRoom);
                    }
                    onlineUserPage.getvBox().getChildren().clear();
                    chatRoom.getChatRoomGUI().init(onlineUserPage.getvBox());
                }
            });
            Platform.runLater(() -> {
                onlineUserPage.getList().getItems().add(label);
                Hoverable.setMouseHandler(label);
            });
        }
    }

    public void addFriend(Player player){
        client.getPlayer().addFriend(player);
        Label label = new Label(player.getId());
        label.setOnMouseClicked(event -> {
            //TODO
        });
        friendPageGUI.addItem(label);
    }

    public ChatGUI getChatGUI() {
        return chatGUI;
    }

    public FriendRequestPage getFriendRequestPage() {
        return friendRequestPage;
    }

    public FriendPageGUI getFriendPageGUI() {
        return friendPageGUI;
    }

    public OnlineUserPage getOnlineUserPage() {
        return onlineUserPage;
    }

    public MultiplayerFarmGUI getMultiplayerFarmGUI() {
        return multiplayerFarmGUI;
    }
}
