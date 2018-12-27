package model;

import java.util.Map;

public class Truck extends Vehicle {



    private static final  int A = 30 ;
    private static final  int B = 30 ;
    private static final  int C = 30 ;
    private final static int upgradeCost = 50;

    public Truck() {
        super();
        vehicleType = VehicleType.TRUCK;
    }

    @Override
    public int getProductsPrice(){

        int price = 0;
        for (Map.Entry<ProductType, Integer> content : contents.entrySet()) {
            price += content.getKey().getSaleCost() * content.getValue();
        }
        return price;
    }

    @Override
    public int getUpgradeCost(){

        return upgradeCost * (int) Math.pow(2,level) + 200 - 50 * (level);
    }
}
