package multiplayer;

import multiplayer.multiplayerModel.messages.Message;

import java.io.*;
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
        System.out.println("started receiver thread");
        while(true) {
            try {
                Object o;
                if((o = objectInputStream.readObject()) instanceof Message){
                    System.out.println("got message");
                    handler.handle((Message) o);
                }
            } catch (IOException e) {

            } catch (ClassNotFoundException e) {

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
