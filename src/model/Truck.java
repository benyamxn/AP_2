package model;

public class Truck extends Vehicle {



    private static final  int A = 30 ;
    private static final  int B = 30 ;
    private static final  int C = 30 ;

    private final static int upgradeCost = 50;


    @Override
    public int getProductsPrice(){

        int price = 0;
        for (ProductType content : contents) {
            price += content.getSaleCost();
        }
        return price;
    }

    @Override
    public int getUpgradeCost(){

        return upgradeCost * (int) Math.pow(2,level) + 200 - 50 * (level);
    }
}
