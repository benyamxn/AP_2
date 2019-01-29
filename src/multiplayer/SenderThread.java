package multiplayer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class SenderThread extends Thread {
    private Queue<Serializable> queue = new LinkedList<>();
    private Socket socket;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;

    public SenderThread(Socket socket) {
        this.socket = socket;
        try {
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: handle here
        }

    }

    public void addToQueue(Serializable serializable) {
        queue.add(serializable);
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
}
