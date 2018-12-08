package model;

import java.util.ArrayList;

public class Helicopter extends Vehicle {



    private static final int UPGRADECOST = 100;

    @Override
    public int getUpgradeCost(){

            return UPGRADECOST * (int) Math.pow(2,level)  + 200 - 50 * (level);
    }

    @Override
    public  int getProductsPrice(){
        int price = 0;
        for (ProductType content : contents) {

           price += content.getBuyCost();
        }
        return price;
    }


    public ArrayList<Product> receiveProducts(){

        ArrayList<Product> receive = new ArrayList<>();
        for (ProductType content : contents) {
            Product temp  = new Product(content);
            receive.add(temp);
        }

        return receive;
    }





}
