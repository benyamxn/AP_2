package multiplayer.server;

import model.ProductType;
import multiplayer.Player;
import multiplayer.RecieverThread;
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
    private ServerHandler serverHandler;
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
                        System.out.println("salam");
                        send(new String("ok"),user.getObjectOutputStream());
                        Player player = getNewPlayer(user.getSocket(),user);
//                        System.out.println("moh");
                        user.setPlayer(player);
                        if(checkNewUser(user)){
                            users.add(user);
                            send(new String("ok"),user.getObjectOutputStream());
                            Thread receiver = new RecieverThread(serverHandler,user.getSocket());
                            ((RecieverThread) receiver).setObjectInputStream(user.getObjectInputStream());
                            receiver.start();
                        }else{
                            send(new String("dddd"),user.getObjectOutputStream());
                            user.getSocket().close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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


    public User getUserById(CompactProfile compactProfile){
        for (User user : users) {
            if(user.getPlayer().getId().equals(compactProfile.getId())){
                return user;
            }
        }
        return  null;
    }

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }
}
