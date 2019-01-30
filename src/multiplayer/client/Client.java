package multiplayer.client;

import model.exception.UsedIdException;
import multiplayer.Player;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private  Socket socket;
    private  ObjectOutputStream objectOutputStream;
    private  Player player;
    private InetAddress ip;
    private int localPort;
    private int serverPort;
    private InetAddress serverIp;
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Client(Player player, InetAddress ip, int localPort, int serverPort, InetAddress serverIp) throws IOException, UsedIdException {
        this.player = player;
        this.ip = ip;
        this.localPort = localPort;
        this.serverPort = serverPort;
        this.serverIp = serverIp;

        socket = new Socket(serverIp, serverPort);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(player);
        String str = (String) getObject();
        if (str.equals("ok")) {

        } else {
            throw new UsedIdException();
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public  Object getObject(){
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
