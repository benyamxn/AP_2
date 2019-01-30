package multiplayer.client;

import GUI.FarmGUI;
import controller.Controller;
import multiplayer.Handler;
import multiplayer.multiplayerModel.messages.ChatMessage;

import java.io.Serializable;

public class ClientHandler implements Handler {

    private Controller controller;
    private FarmGUI farmGUI;

    public ClientHandler() {

    }

    @Override
    public void handle(Serializable object) {


    }
}
