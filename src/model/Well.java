package model;

public class Well implements Upgradable {
    private int level = 1, refillPrice, capacity, remainingWater;
    private static final int WATER_CONSUMPTION = 9;


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

    public void reduceWater(int numberOfCells){
        remainingWater -= numberOfCells;
    }

    public void reduceWater(){
        remainingWater -= WATER_CONSUMPTION;
    }

    public boolean hasEnoughWater(int numberOfCells){
        return remainingWater >= numberOfCells;
    }

    public boolean hasEnoughWater(){
        return remainingWater >= WATER_CONSUMPTION;
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

    public String getStatus() {
        String status = "Well:\n";
        status += "\tLevel: " + level + "\n";
        status += "\tRemaining Water: " + remainingWater + " of " + capacity + "\n";
        return status;
    }
}
