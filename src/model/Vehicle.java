package model;

import java.util.ArrayList;
import java.util.HashMap;

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
        if(! onTravel) {
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
        contents = null;
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

}
