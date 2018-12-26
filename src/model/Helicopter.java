package model;

import java.util.ArrayList;
import java.util.Map;

public class Helicopter extends Vehicle {

    private static final int UPGRADECOST = 100;

    @Override
    public int getUpgradeCost(){

            return UPGRADECOST * (int) Math.pow(2,level)  + 200 - 50 * (level);
    }

    @Override
    public  int getProductsPrice(){
        int price = 0;
        for (Map.Entry<ProductType, Integer> content : contents.entrySet()) {
            price += content.getKey().getBuyCost() * content.getValue();
        }
        return price;
    }


    public ArrayList<Product> receiveProducts(){
        ArrayList<Product> receive = new ArrayList<>();
        for (Map.Entry<ProductType, Integer> content : contents.entrySet()) {
            for (Integer i = 0; i < content.getValue(); i++) {
                receive.add(new Product(content.getKey()));
            }
        }
        return receive;
    }
}
