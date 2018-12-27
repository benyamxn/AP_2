package model;

public class Well {
    private int level = 1, refillPrice, capacity, remainingWater;

    Well(int capacity){
        this.capacity = capacity;
        remainingWater = capacity;
        refillPrice = capacity * 40 / 100;
    }

    public void upgrade(){
        level++;
        refillPrice -= 10;
        capacity += level * 20;
    }

    public int getUpgradeCost(){
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

    public String getStatus() {
        String status = "Well:\n";
        status += "\tLevel: " + level + "\n";
        status += "\tRemaining Water: " + remainingWater + " of " + capacity + "\n";
        return status;
    }
}
