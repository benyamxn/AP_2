package multiplayer.multiplayerGUI;

import GUI.*;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import multiplayer.ClientSenderThread;
import multiplayer.multiplayerModel.messages.StatusUpdateMessage;

import java.io.FileNotFoundException;

public class MultiplayerFarmGUI extends FarmGUI {

    private VBox menuBox;
    private ClientPageGUI clientPageGUI;
    private Button back = new Button("Back");
    public MultiplayerFarmGUI(Controller controller,VBox vBox,ClientPageGUI clientPageGUI) throws FileNotFoundException {
        super(controller);
        this.menuBox = vBox;
        this.clientPageGUI = clientPageGUI;
        createMultiPlayerMenu();
    }

    @Override
    protected void createTruckGUI() {
        VehicleGUI truckGUI = new VehicleGUI(farm.getTruck(), (int) (MainStage.getInstance().getWidth() / 10));
        truckGUI.setOnClick(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            pauseFarm();
            new TruckMenuMultiplayer(game,this);
        });
        UpgradeButton upgradeButton = createVehicleUpgradeButton(truckGUI);
        truckGUI.relocate(MainStage.getInstance().getWidth() / 5, MainStage.getInstance().getHeight() * 0.85);
        upgradeButton.relocate(MainStage.getInstance().getWidth() / 5 - 60, MainStage.getInstance().getHeight()* 0.90);
        truckGUI.addToRoot(anchorPane);
    }

    @Override
    protected void createHelicopterGUI() {
        VehicleGUI helicopterGUI = new VehicleGUI(farm.getHelicopter(), (int) (MainStage.getInstance().getWidth() / 10));
        helicopterGUI.relocate(MainStage.getInstance().getWidth() * 0.7, MainStage.getInstance().getHeight() * 0.85);
        UpgradeButton upgradeButton = createVehicleUpgradeButton(helicopterGUI);
        helicopterGUI.setOnClick(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            pauseFarm();
            new HelicopterMenuMultiplayer(game,this);

        });
        upgradeButton.relocate(MainStage.getInstance().getWidth() * 0.7 - 60, MainStage.getInstance().getHeight()* 0.90);
        helicopterGUI.addToRoot(anchorPane);
    }


    public void createMultiPlayerMenu(){
        menuBox.getChildren().clear();
        menuBox.setVisible(false);
        back.setVisible(false);
        Hoverable.setMouseHandler(back);
        Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 10);
        VBox vBox = new VBox();

        HBox buttons = new HBox();

        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);
        menuBox.setId("menuBox");
        double width = MainStage.getInstance().getWidth();
        double height = MainStage.getInstance().getHeight();
        double anchor = (400 > height / 3)? height / 2 - 200: height / 3;
        anchorPane.setBottomAnchor(vBox, anchor);
        anchorPane.setTopAnchor(vBox, anchor);
        anchorPane.setRightAnchor(vBox, width - width / 9);
        anchorPane.setLeftAnchor(vBox, 0.0);

        Button multiPlayer = new Button("Menu");
        Hoverable.setMouseHandler(multiPlayer);
        menuBox.setPrefHeight(height - 2 * anchor);
        menuBox.setPrefWidth(vBox.getPrefWidth());
        menuBox.setMaxHeight(height - 2 * anchor);
        menuBox.setMaxWidth(width / 9);
        buttons.getChildren().addAll(multiPlayer,back);

        multiPlayer.setOnMouseClicked(event -> {
            if (!menuBox.isVisible()) {
                menuBox.setVisible(true);
                initMenu();
            }
            else{
                menuBox.setVisible(false);
                back.setVisible(false);
            }

        });
        vBox.getChildren().addAll(buttons,menuBox);
        anchorPane.getChildren().add(vBox);
    }

    public void initMenu(){
        menuBox.getChildren().clear();
        Button myFriend = new Button ("myFriend");
        Button chats = new Button("Chats");
        Button leaderBoard = new Button("LeaderBoard");
        Button request = new Button ("Request Page");
        Hoverable.setMouseHandler(myFriend);
        Hoverable.setMouseHandler(leaderBoard);
        Hoverable.setMouseHandler(chats);
        myFriend.setOnMouseClicked(event -> {
            menuBox.getChildren().clear();
            clientPageGUI.getFriendPageGUI().init(menuBox);
            back.setVisible(true);
            back.setOnMouseClicked(event1 -> {
                initMenu();
            });
        });
        chats.setOnMouseClicked(event -> {
            menuBox.getChildren().clear();
            clientPageGUI.getChatGUI().init(menuBox);
            back.setVisible(true);
            back.setOnMouseClicked(event1 -> {
                initMenu();
            });
        });
        request.setOnMouseClicked(event -> {
            menuBox.getChildren().clear();
            clientPageGUI.getFriendRequestPage().init(menuBox);
            back.setVisible(true);
            back.setOnMouseClicked(event1 -> {
                initMenu();
            });
        });
        Hoverable.setMouseHandler(request);

        leaderBoard.setOnMouseClicked(event -> {
            menuBox.getChildren().clear();
            clientPageGUI.getLeaderboardGUIClient().useVBox(menuBox);
            clientPageGUI.getLeaderboardGUIClient().setPossiblePane(anchorPane);
            back.setVisible(true);
            back.setOnMouseClicked(event1 -> {
                initMenu();
            });
        });


        VBox.setMargin(chats,new Insets(10,10,0,10));
        VBox.setMargin(myFriend,new Insets(10,10,0,10));
        VBox.setMargin(leaderBoard,new Insets(10,10,0,10));
        VBox.setMargin(request,new Insets(10,10,0,10));

        menuBox.getChildren().addAll(chats,myFriend,leaderBoard,request);
    }





    @Override
    protected void createGameUpdater() {
        DurationManager durationManager = new DurationManager(this);
        gameUpdater = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            game.updateGame();
            missionGUI.updateMoney();
            missionGUI.updateProducts();
            ClientSenderThread.getInstance().addToQueue(new StatusUpdateMessage(game.getMoney(), game.getMission().getLevel()));
            if(game.isFinished()){
                createPausePage();
                System.out.println("level finished");
                pauseFarm();
                anchorPane.getChildren().add(pauseRectangle);
                gameMenuGUI.showExit(true,menuBox,clientPageGUI);
            }
        }));
        gameUpdater.setRate(durationManager.getRate());
        gameUpdater.setCycleCount(Animation.INDEFINITE);
        gameUpdater.play();

        createGameSpeedSlider(durationManager);
    }
}
