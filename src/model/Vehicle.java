package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Vehicle {

    private static final int A = 30;
    private static final int B = 30;
    private static final int C = 30;
    private static final int TIME = 10;
    private static final int BASETIME = 70;

    protected HashMap<ProductType,Integer> contents = new HashMap<>();
    protected int level = 1;
    protected int estimatedTimeOfArrival;
    protected int arrivalTime;
    protected boolean onTravel = false;
    protected VehicleType vehicleType;
    public abstract int getUpgradeCost();

    public void upgrade() {
        level++;
    }

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
        String status = this.toString() + ":\n" + "\tLevel:" + level + "\n";
        if (onTravel)
            status += "Is travelling";
        else
            status += "Is not travelling";
        status += "Contents:\n";
        for (Map.Entry<ProductType, Integer> content : contents.entrySet()) {
            status += content.getKey().toString() + "(" + content.getValue() + ")\n";
        }
        status += "Time left to arrive: " + estimatedTimeOfArrival + " of " + arrivalTime + "\n";
        return status;
    }
}
