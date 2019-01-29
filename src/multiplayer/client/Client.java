package multiplayer.client;

import multiplayer.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

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

    public  void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        player = new Player(scanner.nextLine(), scanner.nextLine());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //TODO inetADress,localPort....
                    socket = new Socket(serverIp,serverPort,ip,localPort);
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(player);
                    String str = (String) getObject();
                    if (str.equals("ok")) {
                        System.out.println("ok");
                    } else {
                        System.out.println("this id has already used");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
