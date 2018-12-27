package model;

public class Well {
    private int level = 0, refillPrice, capacity, remainingWater;

    Well(int capacity){
        this.capacity = capacity;
        remainingWater = capacity;
        refillPrice = capacity * 40 / 100;
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

    public int getRefillPrice() {
        return refillPrice;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRemainingWater() {
        return remainingWater;
    }
}
