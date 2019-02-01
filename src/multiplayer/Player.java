package multiplayer;

import model.Game;
import multiplayer.server.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player implements Serializable {

    private String name;
    private String id;
    private List<Player> friends =   Collections.synchronizedList(new ArrayList<>());
    private int level = 1;
    private int money;
    private int numberOfExchanges = 0;

    public Player(String name, String id,int money) {
        this.name = name;
        this.id = id;
        this.money = money;
    }

    public void update(Game game){
        if(level != game.getMission().getLevel()) {
            this.level = game.getMission().getLevel();
        }
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Player)){
            return false;
        }
        if(((Player) o).getId().equals(this.id) ){
            return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public void increaseExchange(){
        numberOfExchanges++;
    }

    public int getNumberOfExchanges() {
        return numberOfExchanges;
    }

    public void addFriend(Player player){
        friends.add(player);
    }

    public int numberOfFriends(){
        return friends.size();
    }

}
