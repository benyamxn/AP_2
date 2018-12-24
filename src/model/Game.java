package model;

import java.util.HashMap;
import java.util.Map;

public class Game {

    private int money = 0;
    private int time = 0;
    private Misson misson;

    private Map<ProductType,Integer> products = new HashMap<>();
    private Farm farm = new Farm();
    private String playerName;

    Game(Misson misson ,String playerName){
        this.misson = misson;
        this.playerName = playerName;
    }

    public void updateGame(){
            //TODO

    }

    public boolean checkMisson(){
        Misson temp = new Misson(money,time,products);
        if(temp.compareTo(misson) == 1){
            return true;
        }
        return false;
    }

    public void well(){
        //TODO

    }

}
