package multiplayer;

import multiplayer.multiplayerModel.messages.Message;

import java.io.Serializable;

public interface Handler {

     void handle(Message serializable);
}
