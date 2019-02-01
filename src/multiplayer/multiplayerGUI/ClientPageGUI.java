package multiplayer.multiplayerGUI;

import GUI.FarmGUI;
import GUI.MainStage;
import controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Mission;
import multiplayer.client.Client;
import multiplayer.multiplayerModel.ChatRoom;

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
    private ChatGUI chatGUI;
    public ClientPageGUI(Client client) {
        this.client = client;
        client.setClientPageGUI(this);
        loadBackground();
        createLeaderboard();
        createChatGUI();
        createButtons();
        MainStage.getInstance().pushStack(pane);
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
        VBox vBox = new VBox();
        ChatGUI chatGUI  = new ChatGUI();
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
    }

    private void createButtons() {
        Button startGameButton = new Button("Start the game");
        pane.getChildren().add(startGameButton);
        startGameButton.relocate(width * 0.8, height * 0.9);
        startGameButton.setOnMouseClicked(event -> {
            Controller c = new Controller();
            c.getGame().setMission(Mission.getMissions().get(0));
            try {
                new MultiplayerFarmGUI(c).render();
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

}
