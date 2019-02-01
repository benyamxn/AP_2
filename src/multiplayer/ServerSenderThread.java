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
            synchronized (queue) {
                if (queue.size() > 0) {
                    try {
                        Packet packet = queue.remove();
                        if (packet.getObjectOutputStream() != null) {
                            packet.getObjectOutputStream().writeObject(packet.getMessage());
                        } else {
                            Message message = packet.getMessage();
                            for (User user : users) {
                                if(!user.getSocket().isClosed()) {
                                    user.getObjectOutputStream().writeObject(message);
                                    user.getObjectOutputStream().flush();
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        // TODO: and handle this
                    }
                }
            }
        }
    }

    public static void init(){
        instance = new ServerSenderThread(new ArrayList<>());
    }

    public static ServerSenderThread getInstance(){
        if (instance == null)
            init();
        return instance;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
