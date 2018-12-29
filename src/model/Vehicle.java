package model;

import java.util.HashMap;
import java.util.Map;

public abstract class Vehicle implements Upgradable {

    private static final int A = 30;
    private static final int B = 30;
    private static final int C = 30;
    private static final int TIME = 10;
    private static final int BASETIME = 70;

    protected HashMap<ProductType,Integer> contents = new HashMap<>();
    protected int level = 1;
    protected int estimatedTimeOfArrival;
    protected int arrivalTime = BASETIME;
    protected boolean onTravel = false;
    protected VehicleType vehicleType;

    @Override
    public abstract int getUpgradePrice();

    @Override
    public  boolean canUpgrade(){
        return level < 5;
    }

    @Override
    public void upgrade() { level++; }


    public abstract int getProductsPrice();


    public double getCapacity() {
        return A * level * level + B * level + C;
    }

    public int getEstimatedTimeOfArrival() {
        return estimatedTimeOfArrival;
    }


    public void startTravel() {
        if(!onTravel) {
            setEstimatedTimeOfArrival(arrivalTime);
            onTravel = true;
        }
    }


    public void setEstimatedTimeOfArrival(int estimatedTimeOfArrival) {
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }

    public int getArrivalTime() {

        return BASETIME - level * TIME;
    }


    public void decreaseEstimatedTimeOfArrival(int time) {

        this.estimatedTimeOfArrival -= time;
    }

    public boolean hasArrived() {
        if (estimatedTimeOfArrival <= 0) {
            return true;
        }
        return false;
    }

    public void finishTravel(){
        contents = new HashMap<>();
        onTravel = false;
    }

    public boolean isOnTravel() {
        return onTravel;
    }

    public void addProduct(ProductType productType, int number){
        if (contents.get(productType) != null) {
            contents.put(productType,contents.get(productType) + number);
        } else {
            contents.put(productType, number);
        }
    }

    public double getProductsSize() {
        double size = 0;
        for (Map.Entry<ProductType, Integer> productTypeIntegerEntry : contents.entrySet()) {
            size += productTypeIntegerEntry.getKey().getDepotSize() * productTypeIntegerEntry.getValue();
        }
        return size;
    }

    public HashMap<ProductType, Integer> empty() {
        HashMap<ProductType, Integer> temp = contents;
        finishTravel();
        return temp;
    }

    public String getStatus(){
        String status = this.toString() + ":\n" + "\tLevel: " + level + "\n";
        if (onTravel)
            status += "\tIs travelling";
        else
            status += "\tIs not travelling";
        status += "\n\tContents:\n";
        for (Map.Entry<ProductType, Integer> content : contents.entrySet()) {
            status += "\t" + content.getKey().toString() + " -> " + content.getValue() + "\n";
        }
        status += "\tTime left to arrive: " + estimatedTimeOfArrival + " of " + arrivalTime + "\n";
        return status;
    }
}
