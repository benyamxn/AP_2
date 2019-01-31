package multiplayer.client;

import GUI.FarmGUI;
import controller.Controller;
import multiplayer.ClientSenderThread;
import multiplayer.Handler;
import multiplayer.RecieverThread;
import multiplayer.multiplayerModel.messages.ChatMessage;

import java.io.IOException;
import java.io.Serializable;

public class ClientHandler implements Handler {

    private Controller controller;
    private FarmGUI farmGUI;
    private Client client;

    public ClientHandler(Client client) {

            ClientSenderThread.init(client.getSocket());
            ClientSenderThread.getInstance().start();
             Thread receiver = new RecieverThread(this,client.getSocket());
             ((RecieverThread) receiver).setObjectInputStream(client.getObjectInputStream());
//             receiver.start();
    }

    @Override
    public void handle(Serializable input) {
        if (input instanceof ChatMessage){
            client.getChatRoomByReceiver(((ChatMessage) input).getSender()).addMessage((ChatMessage)input);
        }
    }


}
