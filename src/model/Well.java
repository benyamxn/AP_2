package model;

public class Well implements Upgradable {
    private int level = 1, refillPrice, capacity, remainingWater;

    Well(int capacity){
        this.capacity = capacity;
        remainingWater = capacity;
        refillPrice = capacity * 40 / 100;
    }
    @Override
    public void upgrade(){
        level++;
        refillPrice -= 10;
        capacity += level * 20;
    }

    @Override
    public int getUpgradePrice() {
        return 100*level + 150;
    }

    @Override
    public boolean canUpgrade() {
        return level < 5;
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
