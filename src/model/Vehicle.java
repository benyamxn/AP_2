package model;

import java.util.ArrayList;

public abstract class Vehicle {

    private static final int A = 30;
    private static final int B = 30;
    private static final int C = 30;
    private static final int TIME = 10;
    private static final int BASETIME = 70;

    protected ArrayList<ProductType> contents = new ArrayList<>();
    protected int level = 1;
    protected int estimatedTimeOfArrival;
    protected int arrivalTime;
    protected boolean onTravel = false;

    public abstract int getUpgradeCost();

    public void upgrade() {
        level++;
    }

    public abstract int getProductsPrice();


    public int getCapacity() {

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

    public boolean isOnTravel() {
        return onTravel;
    }
}
