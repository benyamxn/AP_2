package multiplayer.client;

import GUI.FarmCityView;
import GUI.FarmGUI;
import GUI.MainStage;
import controller.Controller;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import model.VehicleType;
import model.exception.NotEnoughCapacityException;
import model.exception.VehicleOnTripException;
import multiplayer.ClientSenderThread;
import multiplayer.Handler;
import multiplayer.Player;
import multiplayer.RecieverThread;
import multiplayer.multiplayerGUI.ChatRoomGUI;
import multiplayer.multiplayerGUI.ClientPageGUI;
import multiplayer.multiplayerGUI.HelicopterMenuTableMultiplayer;
import multiplayer.multiplayerGUI.ProfileGUI;
import multiplayer.multiplayerGUI.TruckMenuTableMultiplayer;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.messages.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientHandler implements Handler {

    private Controller controller;
    private static FarmGUI farmGUI;
    private Client client;
    private static TruckMenuTableMultiplayer truckMenuTableMultiplayer;
    private static HelicopterMenuTableMultiplayer helicopterMenuTableMultiplayer;

    public ClientHandler(Client client) {
            this.client = client;
            ClientSenderThread.init(client.getSocket(),new CompactProfile(client.getPlayer().getName(),client.getPlayer().getId()));
            ClientSenderThread.getInstance().start();
            Thread receiver = new RecieverThread(this,client.getSocket());
            ((RecieverThread) receiver).setObjectInputStream(client.getObjectInputStream());
            receiver.start();
            ClientSenderThread.getInstance().sendReceiverStartedMessage();
    }

    @Override
    public void handle(Message input) {
        if (input instanceof ChatMessage){
            if(((ChatMessage)input).isGlobal()){
                client.getChatRoomByReceiver(null).addMessage((ChatMessage)input);
            } else {
                ChatRoom chatRoom ;
                if((chatRoom = client.getChatRoomByReceiver(((ChatMessage) input).getSender())) == null){
                   chatRoom = new ChatRoom(true,input.getSender());
                   new ChatRoomGUI(chatRoom);
                   client.getChatRooms().add(chatRoom);
                   client.getClientPageGUI().getChatGUI().addChat(chatRoom);
                }
                chatRoom.addMessage((ChatMessage) input);
            }
        }
        if (input instanceof ReceiverStartedMessage) {
            ClientSenderThread.getInstance().setReceiverStarted(true);
            System.out.println("sender thread activated!");
        }
        if (input instanceof LeaderboardStat) {
            try {
                client.getClientPageGUI().getLeaderboardGUIClient().getLeaderboardTable().fillTableObservableList(((LeaderboardStat) input).getPlayersStatus());
                client.getClientPageGUI().getLeaderboardGUIClient().getLeaderboardTable().update();
                System.out.println("leaderboard updated.");
            } catch (NullPointerException e) {
                System.out.println("caught null pointer in updating leaderboard");
            }
        }
        if(input instanceof FriendRequest){
            //TODO friendRequest...
        }
        if(input instanceof FriendAcceptRequest){
            client.getPlayer().addFriend(((FriendAcceptRequest) input).getNewfriend());
        }

        if(input instanceof SendPreviousMessagesRequest){
            for (ChatMessage message : ((SendPreviousMessagesRequest) input).getMessages()) {
                client.getChatRoomByReceiver(null).addMessage(message);
            }
        }
        if (input instanceof ProfileReady) {
            ProfileGUI profileGUI = new ProfileGUI(((ProfileReady) input).getPlayer());
            Platform.runLater(() -> {
                profileGUI.init(new VBox());
                profileGUI.addToRoot(client.getClientPageGUI().getPane());
                profileGUI.relocate(100, 100);
            });
            System.out.println("showing the profile");
        }
        if (input instanceof TruckItemsMessage) {
            if (truckMenuTableMultiplayer != null) {
                truckMenuTableMultiplayer.updatePrices(((TruckItemsMessage) input).getProducts());
            }
            System.out.println("truck product list received.");
        }
        if (input instanceof HelicopterProductsMessage) {
            if (helicopterMenuTableMultiplayer != null) {
                helicopterMenuTableMultiplayer.updatePrices(((HelicopterProductsMessage) input).getProducts());
            }
            System.out.println("helicopter products received.");
        }
        if (input instanceof BuyingValidationMessage) {
            if (((BuyingValidationMessage) input).isBuyingValidationMessage()) {
                Platform.runLater(() -> {
                    FarmGUI.getSoundPlayer().playTrack("click");
//                    FarmGUI.getSoundPlayer().playTrack("helicopter travel");
                    MainStage.getInstance().popStack();
                    farmGUI.resume();
                    farmGUI.getGame().getFarm().getHelicopter().startTravel();
                    FarmCityView.getInstance().runHelicopter(farmGUI.getGame().getFarm().getHelicopter().canTravel());
                    System.out.println("started the travel");
                });
            } else {
                FarmGUI.getSoundPlayer().playTrack("click");
                try {
                    farmGUI.getGame().clear(VehicleType.HELICOPTER);
                } catch (VehicleOnTripException e) {
                    e.printStackTrace();
                } catch (NotEnoughCapacityException e) {
                    e.printStackTrace();
                }
                farmGUI.resume();
                MainStage.getInstance().popStack();
            }
        }

        if(input instanceof OnlineUserRequest){
            System.out.println("get onlineuser");
            client.getClientPageGUI().update(new ArrayList<Player>(((OnlineUserRequest) input).getPlayers()));
        }
    }

    public static void setTruckMenuTableMultiplayer(TruckMenuTableMultiplayer truckMenuTableMultiplayer1) {
        truckMenuTableMultiplayer = truckMenuTableMultiplayer1;
    }

    public static void setHelicopterMenuTableMultiplayer(HelicopterMenuTableMultiplayer helicopterMenuTableMultiplayer) {
        ClientHandler.helicopterMenuTableMultiplayer = helicopterMenuTableMultiplayer;
    }

    public static void setFarmGUI(FarmGUI farmGUI) {
        ClientHandler.farmGUI = farmGUI;
    }
}
