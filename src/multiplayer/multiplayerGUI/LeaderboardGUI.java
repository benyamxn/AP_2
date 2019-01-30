package multiplayer.multiplayerGUI;

import multiplayer.ClientSenderThread;
import multiplayer.messages.LeaderboardRequestMessage;

public class LeaderboardGUI {
    private static boolean visible = false;
    private ClientSenderThread senderThread = ClientSenderThread.getInstance();

    public LeaderboardGUI() {
        senderThread.addToQueue(new LeaderboardRequestMessage());
    }
}
