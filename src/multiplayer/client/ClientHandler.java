package multiplayer.client;

import GUI.FarmGUI;
import controller.Controller;
import multiplayer.ClientSenderThread;
import multiplayer.Handler;
import multiplayer.RecieverThread;
import multiplayer.multiplayerGUI.ClientPageGUI;
import multiplayer.multiplayerModel.messages.ChatMessage;
import multiplayer.multiplayerModel.messages.LeaderboardStat;
import multiplayer.multiplayerModel.messages.ReceiverStartedMessage;

import java.io.IOException;
import java.io.Serializable;

public class ClientHandler implements Handler {

    private Controller controller;
    private FarmGUI farmGUI;
    private Client client;
    private ClientPageGUI clientPageGUI;

    public ClientHandler(Client client) {

            ClientSenderThread.init(client.getSocket(), client.getCompactProfile());
            ClientSenderThread.getInstance().start();
            Thread receiver = new RecieverThread(this,client.getSocket());
            ((RecieverThread) receiver).setObjectInputStream(client.getObjectInputStream());
            receiver.start();
            ClientSenderThread.getInstance().addToQueue(new ReceiverStartedMessage());
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
            ClientSenderThread.getInstance().addToQueue(new ReceiverStartedMessage());
            System.out.println("receiver started message");
        }
        if (input instanceof LeaderboardStat) {
            System.out.println("leaderboard stat arrived");
            if (clientPageGUI != null) {
                System.out.println("not null");
                clientPageGUI.getLeaderboardGUIClient().getLeaderboardTable().fillTableObservableList(((LeaderboardStat) input).getPlayersStatus());
                clientPageGUI.getLeaderboardGUIClient().getLeaderboardTable().update();
            }
        }
    }

    public void setClientPageGUI(ClientPageGUI clientPageGUI) {
        this.clientPageGUI = clientPageGUI;
    }
}
