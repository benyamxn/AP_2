package multiplayer.server;

import model.ProductType;
import multiplayer.Packet;
import multiplayer.Player;
import multiplayer.RecieverThread;
import multiplayer.ServerSenderThread;
import multiplayer.client.Client;
import multiplayer.multiplayerGUI.ServerPageGUI;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.Shop;
import multiplayer.multiplayerModel.messages.*;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

public class Server {

    private List<User> users =   Collections.synchronizedList(new ArrayList<>());
    private ServerSocket serverSocket ;
    private ArrayList<ChatRoom> chatRooms;
    private Shop shop;
    private ServerHandler serverHandler;
    private ServerPageGUI serverPageGUI;
    public Server(int port,InetAddress inetAddress) throws IOException {

        this.serverSocket = new ServerSocket(port);
        this.chatRooms = new ArrayList<>();
        chatRooms.add(new ChatRoom(false,null));
        Thread newUsersThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        User user = new User();
                        user.setSocket(serverSocket.accept());
                        send(new String("ok"),user.getObjectOutputStream());
                        Player player = getNewPlayer(user.getSocket(),user);
                        user.setPlayer(player);
                        if(checkNewUser(user)){
                            users.add(user);
                            serverPageGUI.getLeaderboardGUIServer().getLeaderboardTable().addPlayer(user.getPlayer());
                            serverPageGUI.getLeaderboardGUIServer().getLeaderboardTable().update();
                            send(new String("ok"),user.getObjectOutputStream());
                            Thread receiver = new RecieverThread(serverHandler,user.getSocket());
                            ((RecieverThread) receiver).setObjectInputStream(user.getObjectInputStream());
                            receiver.start();
                            send(new ReceiverStartedMessage(), user.getObjectOutputStream());
                            ServerSenderThread.getInstance().addToQueue(new Packet(new LeaderboardStat(getPlayers()), null));
                            // TODO: check the line above
                        } else {
                            send(new String("dddd"),user.getObjectOutputStream());
                            user.getSocket().close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        newUsersThread.setDaemon(true);
        newUsersThread.start();
        createShop();
    }

    public void createShop(){
        EnumMap<ProductType,Integer> enumMap = new EnumMap<>(ProductType.class);
        for (ProductType value : ProductType.values()) {
            enumMap.put(value,10);
        }
        shop = new Shop(enumMap);
    }

    public Player getNewPlayer(Socket socket,User user){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Player player = (Player) objectInputStream.readObject();
            return  player;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkNewUser(User input){
        for (User user : users) {
            if (user.getPlayer().equals(input.getPlayer())) {
                return false;
            }
        }
        return true;
    }

    public void send(Object object,ObjectOutputStream objectOutputStream) {

        try {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public ChatRoom getChatRoomByReceiver(CompactProfile compactProfile){
        for (ChatRoom chatRoom : chatRooms) {
            if((chatRoom.getReceiver() == null && compactProfile == null)  || (chatRoom.getReceiver() != null && chatRoom.getReceiver().equals(compactProfile))){
                return chatRoom;
            }
        }
        return null;
    }

    public Shop getShop() {
        return shop;
    }


    public User getUserById(CompactProfile compactProfile){
        for (User user : users) {
            if(user.getPlayer().getId().equals(compactProfile.getId())){
                return user;
            }
        }
        return  null;
    }

    public User getUserById(String id){
        for (User user : users) {
            if(user.getPlayer().getId().equals(id)){
                return user;
            }
        }
        return  null;
    }


    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for (User user : users) {
            players.add(user.getPlayer());
        }
        return players;
    }

    public ServerPageGUI getServerPageGUI() {
        return serverPageGUI;
    }

    public void setServerPageGUI(ServerPageGUI serverPageGUI) {
        this.serverPageGUI = serverPageGUI;
    }

    public ArrayList<ChatRoom> getChatRooms() {
        return chatRooms;
    }
}
