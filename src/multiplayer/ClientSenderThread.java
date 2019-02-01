package multiplayer;


import multiplayer.multiplayerModel.CompactProfile;
import multiplayer.multiplayerModel.messages.ChatMessage;
import multiplayer.multiplayerModel.messages.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class ClientSenderThread extends Thread {
    private Queue<Message> queue = new LinkedList<>();
    private Socket socket;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private static ClientSenderThread instance;
    private CompactProfile compactProfile;

    private ClientSenderThread(Socket socket,CompactProfile compactProfile) {
        this.socket = socket;
        this.compactProfile = compactProfile;
        try {
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: handle here
        }

    }

    public void addToQueue(Message message) {
        queue.add(message);
    }

    @Override
    public void run() {
        while(true) {
            synchronized (queue) {
                if (queue.size() > 0) {
                    try {
                        Message message = queue.remove();
                        message.setSender(compactProfile);
                        objectOutputStream.writeObject(message);
                        objectOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        // TODO: and handle this
                    }
                }
            }
        }
    }

    public static void init(Socket socket,CompactProfile compactProfile){
        instance = new ClientSenderThread(socket,compactProfile);
    }

    public static ClientSenderThread getInstance() {
        return instance;
    }

    public Queue<Message> getQueue() {
        return queue;
    }
}
