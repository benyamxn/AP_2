package multiplayer.server;

import multiplayer.Handler;
import multiplayer.Packet;
import multiplayer.RecieverThread;
import multiplayer.ServerSenderThread;
import multiplayer.multiplayerModel.messages.ChatMessage;
import multiplayer.multiplayerModel.messages.Message;

import java.io.IOException;
import java.io.Serializable;

public class ServerHandler implements Handler {

    private Server server;

    public ServerHandler(Server server) {
        this.server = server;

        ServerSenderThread.init();
        ServerSenderThread.getInstance().setUsers(server.getUsers());
        ServerSenderThread.getInstance().start();
        server.setServerHandler(this);
    }


    @Override
    public void handle(Serializable input) {
        if (input instanceof ChatMessage){
            System.out.println("send chat");
            sendChatMessage((ChatMessage) input);
//        } else if(){

        }
    }

    public void sendChatMessage(ChatMessage message) {
        if(message.getReceiver() == null){
            ServerSenderThread.getInstance().addToQueue(new Packet(message,null));
        }else{
            try {
                ServerSenderThread.getInstance().addToQueue(new Packet(message,server.getUserById(message.getReceiver()).getObjectOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
