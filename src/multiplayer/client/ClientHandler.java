package multiplayer.client;

import GUI.FarmGUI;
import controller.Controller;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import multiplayer.ClientSenderThread;
import multiplayer.Handler;
import multiplayer.RecieverThread;
import multiplayer.multiplayerGUI.ClientPageGUI;
import multiplayer.multiplayerGUI.ProfileGUI;
import multiplayer.multiplayerGUI.TruckMenuTableMultiplayer;
import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.messages.*;

import java.io.IOException;
import java.io.Serializable;

public class ClientHandler implements Handler {

    private Controller controller;
    private FarmGUI farmGUI;
    private Client client;
    private static TruckMenuTableMultiplayer truckMenuTableMultiplayer;

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
            } else
                client.getChatRoomByReceiver(((ChatMessage) input).getSender()).addMessage((ChatMessage)input);
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
            System.out.println("product list received.");
        }
    }

    public static void setTruckMenuTableMultiplayer(TruckMenuTableMultiplayer truckMenuTableMultiplayer1) {
        truckMenuTableMultiplayer = truckMenuTableMultiplayer1;
    }
}
