package multiplayer.client;

import GUI.FarmGUI;
import controller.Controller;
import multiplayer.ClientSenderThread;
import multiplayer.Handler;
import multiplayer.RecieverThread;
import multiplayer.multiplayerGUI.ClientPageGUI;
import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.messages.ChatMessage;
import multiplayer.multiplayerModel.messages.LeaderboardStat;
import multiplayer.multiplayerModel.messages.ReceiverStartedMessage;

import java.io.IOException;
import java.io.Serializable;

public class ClientHandler implements Handler {

    private Controller controller;
    private FarmGUI farmGUI;
    private Client client;

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
    public void handle(Serializable input) {
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
            client.getClientPageGUI().getLeaderboardGUIClient().getLeaderboardTable().fillTableObservableList(((LeaderboardStat) input).getPlayersStatus());
            client.getClientPageGUI().getLeaderboardGUIClient().getLeaderboardTable().update();
            System.out.println("leaderboard updated.");
        }
    }

}
