package multiplayer;


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

    private ClientSenderThread(Socket socket) {
        this.socket = socket;
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
            if (queue.size() > 0) {
                try {
                    objectOutputStream.writeObject(queue.remove());
                } catch (IOException e) {
                    e.printStackTrace();
                    // TODO: and handle this
                }
            }
        }
    }

    public static void init(Socket socket){
        instance = new ClientSenderThread(socket);
    }

    public static ClientSenderThread getInstance() {
        return instance;
    }
}
