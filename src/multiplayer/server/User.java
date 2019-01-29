package multiplayer.server;

import multiplayer.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class User {

    private Player player;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;

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

    public ObjectOutputStream getObjectOutputStream() throws IOException {
        if (objectOutputStream != null) {
            return objectOutputStream;
        } else {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            return objectOutputStream;
        }
    }

}
