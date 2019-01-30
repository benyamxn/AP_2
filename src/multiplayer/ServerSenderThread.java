package multiplayer;

import GUI.SoundUI;
import javafx.scene.media.Media;
import multiplayer.multiplayerModel.messages.Message;
import multiplayer.server.User;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public  class ServerSenderThread extends Thread {
    private Queue<Packet> queue = new LinkedList<>();
    private ArrayList<User> users;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private static ServerSenderThread instance;

    private ServerSenderThread(ArrayList<User> users) {
        this.users = users;
    }

    public void addToQueue(Packet packet) {
        queue.add(packet);
    }

    @Override
    public void run() {
        while(true) {
            if (queue.size() > 0) {
                try {
                    Packet packet = queue.remove();
                    if (packet.getObjectOutputStream() != null) {
                        packet.getObjectOutputStream().writeObject(packet.getMessage());
                    } else {
                        Message message = packet.getMessage();
                        for (User user : users) {
                            user.getObjectOutputStream().writeObject(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // TODO: and handle this
                }
            }
        }
    }

    public static void init(ArrayList<User> users){
        instance = new ServerSenderThread(users);
    }

    public static ServerSenderThread getInstance(){
        return instance;
    }

}
