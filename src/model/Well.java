package model;

public class Well {
    private int level = 0, refillPrice, capacity, remainingWater;

    Well(int capacity){
        this.capacity = capacity;
        remainingWater = capacity;
    }

    public void upgrade(){
        level++;
        //TODO
        refillPrice -= 10;
        capacity += level * 20;
    }

    public int getUpgradeCost(){
        //TODO

        return 100*level + 150;
    }

    public void refill(){
        remainingWater = capacity;
    }
}
