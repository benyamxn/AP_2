package multiplayer.server;

import model.ProductType;
import multiplayer.Player;
import multiplayer.client.Client;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.Shop;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.EnumMap;

public class Server {

    private ArrayList<User> users = new ArrayList<>();
    private ServerSocket serverSocket ;
    private ArrayList<ChatRoom> chatRooms;
    private Shop shop;
    public Server(int port,InetAddress inetAddress) throws IOException {

        this.serverSocket = new ServerSocket(port);
        this.chatRooms = new ArrayList<>();
        chatRooms.add(new ChatRoom(false,null));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        User user = new User();
                        user.setSocket(serverSocket.accept());
                        user.setPlayer(getNewPlayer(user.getSocket()));
                        if(checkNewUser(user)){
                            users.add(user);
                            send(new String("ok"),user.getSocket());
                        }else{
                            send(new String("dddd"),user.getSocket());
                            user.getSocket().close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        EnumMap<ProductType,Integer> enumMap = new EnumMap<>(ProductType.class);
        enumMap.put(ProductType.EGG,50);
        enumMap.put(ProductType.CAKE,20);
        shop = new Shop(enumMap);
    }

    public Player getNewPlayer(Socket socket){
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Player player = (Player) objectInputStream.readObject();
            return player;
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

    public void send(Object object,Socket socket) {

        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ChatRoom getChatRoomByReceiver(CompactProfile compactProfile){
        for (ChatRoom chatRoom : chatRooms) {
            if((chatRoom.getReceiver() == null && compactProfile == null)  || chatRoom.getReceiver().equals(compactProfile)){
                return chatRoom;
            }
        }
        return null;
    }

    public Shop getShop() {
        return shop;
    }
}
