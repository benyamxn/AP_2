package multiplayer.server;

import multiplayer.*;
import multiplayer.multiplayerModel.messages.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

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
    public void handle(Message input) {
        if (input instanceof ChatMessage){
            System.out.println("send chat");
            sendChatMessage((ChatMessage) input);
//        } else if(){

        }
        if (input instanceof ReceiverStartedMessage) {
            try {
                ServerSenderThread.getInstance().addToQueue(new Packet(new ReceiverStartedMessage(), server.getUserById(((ReceiverStartedMessage) input).getSender()).getObjectOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (input instanceof LeaderboardRequestMessage) {
            try {
                ServerSenderThread.getInstance().addToQueue(new Packet(new LeaderboardStat(server.getPlayers()), server.getUserById(((LeaderboardRequestMessage) input).getSender()).getObjectOutputStream()));
                System.out.println("leaderboard added to queue");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(input instanceof FriendRequest){
            try {
                ServerSenderThread.getInstance().addToQueue(new Packet(input,server.getUserById(((FriendRequest)input).getReceiver()).getObjectOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(input instanceof FriendAcceptRequest){
            Player receiver = server.getUserById(input.getReceiver()).getPlayer();
            Player sender = server.getUserById(input.getSender()).getPlayer();
            sender.addFriend(receiver);
            receiver.addFriend(sender);
            try {
                ((FriendAcceptRequest) input).setNewfriend(sender);
                ServerSenderThread.getInstance().addToQueue(new Packet(input,server.getUserById(((FriendRequest)input).getReceiver()).getObjectOutputStream()));
                ((FriendAcceptRequest) input).setNewfriend(receiver);
                ServerSenderThread.getInstance().addToQueue(new Packet(input,server.getUserById(((FriendRequest)input).getSender()).getObjectOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(input instanceof SendPreviousMessagesRequest){

            List<ChatMessage> messages  = server.getChatRoomByReceiver(null).getChatMessages();
            List<ChatMessage> copy = List.copyOf(messages);
            ((SendPreviousMessagesRequest) input).setMessages(copy);
            try {
                ServerSenderThread.getInstance().addToQueue(new Packet(input,server.getUserById((input).getSender()).getObjectOutputStream()));
                server.getChatRoomByReceiver(null).sendMessage(null,input.getSender().getId() + "joined.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendChatMessage(ChatMessage message) {
        if(message.getReceiver() == null){
            ServerSenderThread.getInstance().addToQueue(new Packet(message,null));
            server.getChatRoomByReceiver(null).addMessage(message);
        }else{
            try {
                ServerSenderThread.getInstance().addToQueue(new Packet(message,server.getUserById(message.getReceiver()).getObjectOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
