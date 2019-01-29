package multiplayer;

import model.Game;

import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private String id;
    private int level;

    public Player(String name, String id) {
        this.name = name;
        this.id = id;
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
}
