package multiplayer.server;

import multiplayer.Handler;
import multiplayer.multiplayerModel.messages.ChatMessage;
import multiplayer.multiplayerModel.messages.Message;

import java.io.Serializable;

public class ServerHandler implements Handler {

    private Server server;
    public ServerHandler(Server server) {
        this.server = server;
    }

    @Override
    public void handle(Serializable input) {
        if (input instanceof ChatMessage){
            sendToAllMesssage((ChatMessage) input);
        }
    }

    public void sendToAllMesssage(ChatMessage message){

    }
}
