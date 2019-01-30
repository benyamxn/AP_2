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

    public RecieverThread(Handler handler,Socket socket) throws IOException {
        this.handler = handler;
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.objectInputStream = new ObjectInputStream(inputStream);
    }

    @Override
    public void run() {
        while(true) {
            try {
                Object o = objectInputStream.read();
                handler.handle((Serializable) o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
