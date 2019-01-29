package multiplayer.server;

import multiplayer.Player;

import java.net.Socket;

public class User {

    private Player player;
    private Socket socket;


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
