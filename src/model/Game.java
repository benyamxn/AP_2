package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.exception.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map;

public class Game {

    private static int BASE_TIME = 12;
    private static int WILD_NUMBER = 12;
    private int money = 0;
    private int time = 0;
    private Mission mission;
    private Map<ProductType,Integer> products = new HashMap<>();
    private Farm farm = new Farm();
    private String playerName = "Guest";
    private ProductType[] marketProducts = {ProductType.EGG, ProductType.WOOL, ProductType.MILK};



    public Game(Mission mission) {
        this.mission = mission;
    }
    public Game(Mission mission, String playerName){
        this.mission = mission;
        this.playerName = playerName;
    }

    public Game(int money, Mission mission, String playerName) {
        this.money = money;
        this.mission = mission;
        this.playerName = playerName;
    }

    public Game(int money, Mission mission) {
        this.money = money;
        this.mission = mission;
    }

    public void loadMarketProducts(String path) throws IOException {
        Reader reader = new FileReader(path);
        Gson gson = new GsonBuilder().create();
        marketProducts = gson.fromJson(reader, ProductType[].class);
        reader.close();
    }

    public void saveMarketProducts(String path) throws IOException {
        Writer writer = new FileWriter(path);
        Gson gson = new GsonBuilder().create();
        gson.toJson(marketProducts, writer);
        writer.close();
    }

    public void setMarketProducts(ProductType... marketProducts) {
        this.marketProducts = marketProducts;
    }

    public void updateGame(){
        Random random = new Random();
        if(random.nextInt(15) % 15 == 0){
            if ((farm.getMap().getNumberOfEachAnimal()[3] < WILD_NUMBER )) {
                for (int i = 0; i < random.nextInt(3) + 1; i++) {
                    farm.placeAnimal(WildType.GRIZZLY);
                }
            }
        }
        time += BASE_TIME;
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

    public boolean isFinished(){
        Mission temp = new Mission(money,time,products);
        if(temp.compareTo(mission) == 1){
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

    public void addProductToVehicle(Vehicle vehicle, ProductType productType, int number) throws VehicleOnTripException, NotEnoughCapacityException, NotEnoughItemsException, MoneyNotEnoughException, ItemNotForSaleException {
        if (vehicle.onTravel) {
            throw new VehicleOnTripException();
        }
        if (vehicle instanceof Helicopter && !Arrays.asList(marketProducts).contains(productType)) {
            throw new ItemNotForSaleException();
        }
        double capacity = number * productType.getDepotSize();
        if (capacity > vehicle.getCapacity()) {
            throw new NotEnoughCapacityException();
        }
        HashMap<ProductType, Integer> products = new HashMap<>();
        products.put(productType, number);
        if (vehicle instanceof Truck) {
            Warehouse warehouse = farm.getWarehouse();
            if (warehouse.hasProducts(products)) {
                warehouse.removeProducts(products);
                vehicle.addProduct(productType, number);
                return;
            } else {
                throw new NotEnoughItemsException();
            }
        }

        if (number * productType.getBuyCost() > money) {
            throw new MoneyNotEnoughException();
        } else {
            money -= number * productType.getBuyCost();
            vehicle.addProduct(productType, number);
        }
    }

    public void moveVehicle(Vehicle vehicle) throws VehicleOnTripException,NotEnoughCapacityException,MoneyNotEnoughException, NotEnoughItemsException {

        if(vehicle.onTravel){
            throw new VehicleOnTripException();
        }
        vehicle.startTravel();

    }

    public int getMoney() {
        return money;
    }

    public void decreaseMoney(int number) {
        money -= number;
    }

    public int getTime() {
        return time;
    }

    public Farm getFarm() {
        return farm;
    }

    public Cell getCell(Point point){
       return  farm.getCell(point);
    }

    public void clear(VehicleType vehicleType) throws VehicleOnTripException, NotEnoughCapacityException {
        Vehicle vehicle = getFarm().getVehicleByName(vehicleType);
        if (vehicle instanceof Helicopter) {
            if (vehicle.isOnTravel())
                throw new VehicleOnTripException();
            money += vehicle.getProductsPrice();
            vehicle.finishTravel();
            return;
        }
        Warehouse warehouse = getFarm().getWarehouse();
        if (warehouse.getCapacity() < vehicle.getProductsSize())
            throw new NotEnoughCapacityException();
        warehouse.addProduct(vehicle.empty());
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }


    public ProductType[] getMarketProducts() {
        return marketProducts;
    }

    public String getStatus() {
        String status = "Game:\n";
        status += "\tTime Passed: " + time + "\n";
        status += "\tMoney: " + money + "\n";
        status += "\tMission: \n";
        Map<ProductType, Integer> goals = mission.getProductsGoal();
        status += "\tTime Goal: " + mission.getTimeGoal() + "\n";
        for (Map.Entry<ProductType, Integer> entry : products.entrySet()) {
            ProductType productType = entry.getKey();
            if (!goals.containsKey(productType))
                continue;
            status += productType.toString() + " : " + entry.getValue() + " out of " + goals.get(productType) + "\n";
        }
        return status;
    }


}
