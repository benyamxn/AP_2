package multiplayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;


public class RecieverThread  extends Thread  {

    private Handler handler;
    private Socket  socket;
    private ObjectInputStream objectInputStream;
    private InputStream inputStream;

    public RecieverThread(Handler handler,Socket socket) {
        this.handler = handler;
        this.socket = socket;
        try {
            this.inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                Object o = objectInputStream.readObject();
                handler.handle((Serializable) o);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }
}
