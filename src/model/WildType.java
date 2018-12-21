package model;

public enum WildType {

    BEAR(),
    POLAR_BEAR();

    private int sellPrice;
    WildType(){
        this.sellPrice = 250;
    }
    WildType(int sellPrice){
        this.sellPrice = sellPrice;
    }
}
