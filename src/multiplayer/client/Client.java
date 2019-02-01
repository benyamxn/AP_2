package multiplayer.client;

import model.exception.UsedIdException;
import multiplayer.Player;
import multiplayer.multiplayerGUI.ClientPageGUI;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerModel.CompactProfile;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Player player;
    private InetAddress ip;
    private int localPort;
    private int serverPort;
    private InetAddress serverIp;
    private ArrayList<ChatRoom> chatRooms = new ArrayList<>();
    private ClientPageGUI clientPageGUI;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Client(Player player, InetAddress ip, int localPort, int serverPort, InetAddress serverIp) throws IOException, UsedIdException {


        this.player = player;
        this.ip = ip;
        this.localPort = localPort;
        this.serverPort = serverPort;
        this.serverIp = serverIp;
        chatRooms.add(new ChatRoom(true,null));
        socket = new Socket(serverIp, serverPort,ip,localPort);

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        try {
            System.out.println(((String) objectInputStream.readObject()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        objectOutputStream.writeObject(player);
        objectOutputStream.flush();
        String str = (String) getObject();
        if (str.equals("ok")) {
            System.out.println("connected to Server");
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
            return objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ChatRoom getChatRoomByReceiver(CompactProfile compactProfile){
        for (ChatRoom chatRoom : chatRooms) {
            if((chatRoom.getReceiver() == null && compactProfile == null)  || ((chatRoom.getReceiver() != null) && chatRoom.getReceiver().equals(compactProfile))){
                return chatRoom;
            }
        }
        return null;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public CompactProfile getCompactProfile() {
        return new CompactProfile(player.getName(), player.getId());
    }

    public ClientPageGUI getClientPageGUI() {
        return clientPageGUI;
    }

    public void setClientPageGUI(ClientPageGUI clientPageGUI) {
        this.clientPageGUI = clientPageGUI;
    }
}
