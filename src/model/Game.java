package model;

import model.exception.MoneyNotEnoughException;
import model.exception.NotEnoughCapacityException;
import model.exception.VehicleOnTripException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private int money = 0;
    private int time = 0;
    private Misson misson;

    private Map<ProductType,Integer> products = new HashMap<>();
    private Farm farm = new Farm();
    private String playerName = "Guest";

    public Game(Misson misson) {
        this.misson = misson;
    }
    public Game(Misson misson , String playerName){
        this.misson = misson;
        this.playerName = playerName;
    }

    public void updateGame(){
        //TODO set time
        farm.updateFarm();
        Truck truck = farm.getTruck();
        if(truck.isOnTravel()){
            if(truck.hasArrived()){
                money += truck.getProductsPrice();
                truck.finishTravel();
            }
        }
        ArrayList<Product> newProducts = null;
        Helicopter helicopter = farm.getHelicopter();
        if(helicopter.isOnTravel()){
            if(helicopter.hasArrived()){
               newProducts = helicopter.receiveProducts();
               helicopter.finishTravel();
            }
        }
        if(newProducts != null){
            farm.placeProduct(newProducts);
        }

    }

    public boolean checkMisson(){
        Misson temp = new Misson(money,time,products);
        if(temp.compareTo(misson) == 1){
            return true;
        }
        return false;
    }

    public void well() throws MoneyNotEnoughException {
        if( money >= farm.getWell().getRefillPrice()) {
            money -= farm.getWell().getRefillPrice();
            farm.getWell().refill();
        }
        else{
            throw new MoneyNotEnoughException();
        }
    }

    public void moveVehicle(Vehicle vehicle,HashMap<ProductType,Integer> products) throws VehicleOnTripException,NotEnoughCapacityException,MoneyNotEnoughException {

        if(vehicle.onTravel){
            throw new VehicleOnTripException();
        }
        else{
            double capacity = 0;
            for (Map.Entry<ProductType, Integer> product : products.entrySet()) {
               capacity += product.getKey().getDepotSize() * product.getValue();
            }
            if(capacity > vehicle.getCapacity()){
                throw new NotEnoughCapacityException();
            }
            else{
                if(vehicle instanceof Helicopter){
                    Helicopter temp  = new Helicopter();
                    temp.startTravel(products);
                    if(temp.getProductsPrice() > money){
                        throw new MoneyNotEnoughException();
                    }
                    else{
                        money -= temp.getProductsPrice();
                    }
                }
                vehicle.startTravel(products);
            }
        }

    }

}
