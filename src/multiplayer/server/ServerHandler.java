package multiplayer.server;

import model.ProductType;
import multiplayer.*;
import multiplayer.multiplayerModel.messages.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
            if(((ChatMessage) input).isGlobal())
                    sendChatMessage((ChatMessage) input);
            else{
                try {
                    ServerSenderThread.getInstance().addToQueue(new Packet(input,server.getUserById(input.getSender()).getObjectOutputStream()));
                    if(!input.getReceiver().equals(input.getSender()))
                          ServerSenderThread.getInstance().addToQueue(new Packet(input,server.getUserById(input.getReceiver()).getObjectOutputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
                ServerSenderThread.getInstance().addToQueue(new Packet(new LeaderboardStat(server.getPlayers()), server.getUserById(((Message) input).getSender()).getObjectOutputStream()));
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
                FriendAcceptRequest temp = new FriendAcceptRequest();
                ((FriendAcceptRequest) input).setNewfriend(sender);
                ServerSenderThread.getInstance().addToQueue(new Packet(input,server.getUserById(((FriendAcceptRequest)input).getReceiver()).getObjectOutputStream()));
                temp.setNewfriend(receiver);
                ServerSenderThread.getInstance().addToQueue(new Packet(temp,server.getUserById(((FriendAcceptRequest)input).getSender()).getObjectOutputStream()));
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
                server.getChatRoomByReceiver(null).sendMessage(null,input.getSender().getId() + "  joined.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (input instanceof ProfileRequestMessage) {
            try {

                Player p  = Player.copyPlayer(server.getUserById(input.getSender()).getPlayer());
                ServerSenderThread.getInstance().addToQueue(new Packet(new ProfileReady(p), server.getUserById(((Message) input).getSender()).getObjectOutputStream()));
                System.out.println(server.getUserById(input.getSender()).getPlayer().getMoney());
                System.out.println("profile sent.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (input instanceof TruckMenuRequest) {
            try {
                ServerSenderThread.getInstance().addToQueue(new Packet(server.getShop().createProductsMessage(), server.getUserById(input.getSender().getId()).getObjectOutputStream()));
                System.out.println("truck items sent.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(input instanceof OnlineUserRequest){
            input.setReceiver(input.getSender());
            ((OnlineUserRequest) input).setPlayers(List.copyOf(server.getPlayers()));
            try {
                ServerSenderThread.getInstance().addToQueue(new Packet(input,server.getUserById(input.getSender()).getObjectOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (input instanceof HelicopterMenuRequest) {
            try {
                ServerSenderThread.getInstance().addToQueue(new Packet(server.getShop().createHelicopterProductsMessage(), server.getUserById(input.getSender().getId()).getObjectOutputStream()));
                System.out.println("helicopter items sent.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (input instanceof HelicopterItems) {
            boolean valid = true;
            for (Map.Entry<ProductType, Integer> entry : ((HelicopterItems) input).getProducts().entrySet()) {
                if (entry.getValue() > server.getShop().getProducts().get(entry.getKey()))
                    valid = false;
            }
            if (valid) {
                for (Map.Entry<ProductType, Integer> entry : ((HelicopterItems) input).getProducts().entrySet()) {
                    server.getShop().getProducts().put(entry.getKey(), server.getShop().getProducts().get(entry.getKey()) - entry.getValue());
                }
                server.getShop().getShopGUI().getTable().refresh();
                ServerSenderThread.getInstance().addToQueue(new Packet(server.getShop().createHelicopterProductsMessage(), null));
                server.getUserById(input.getSender()).getPlayer().increaseExchange();
//                server.getServerPageGUI().getShopGUI().getTable().refresh();
            }
            try {
                ServerSenderThread.getInstance().addToQueue(new Packet(new BuyingValidationMessage(valid), server.getUserById(input.getSender().getId()).getObjectOutputStream()));
                System.out.println("validation sent.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (input instanceof StatusUpdateMessage) {
            server.getUserById(input.getSender()).getPlayer().setStat((((StatusUpdateMessage) input).getMoney()), ((StatusUpdateMessage) input).getLevel());
            server.getServerPageGUI().getLeaderboardGUIServer().getLeaderboardTable().updatePlayer(server.getUserById(input.getSender()).getPlayer());
            ((StatusUpdateMessage) input).whoose = input.getSender().getId();
            ServerSenderThread.getInstance().addToQueue(new Packet(input, null));
        }
        if (input instanceof TruckContentsMessage) {
            for (Map.Entry<ProductType, Integer> entry : ((TruckContentsMessage) input).getContents().entrySet()) {
                server.getShop().getProducts().put(entry.getKey(), server.getShop().getProducts().get(entry.getKey()) + entry.getValue());
            }
            server.getShop().getShopGUI().getTable().refresh();
            ServerSenderThread.getInstance().addToQueue(new Packet(server.getShop().createHelicopterProductsMessage(), null));
            server.getUserById(input.getSender()).getPlayer().increaseExchange();
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
