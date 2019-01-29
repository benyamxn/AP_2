package model;

import GUI.GameStatus;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import model.exception.*;
import java.io.*;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.Map;

public class Game {

    private static int BASE_TIME = 12;
    private static int WILD_NUMBER = 12;
    private int money = 0;
    private int time = 0;
    private Mission mission;
    private EnumMap<ProductType,Integer> products = new EnumMap<>(ProductType.class);
    private Farm farm = new Farm();
    private String playerName = "Guest";
    private ProductType[] marketProducts = {ProductType.EGG, ProductType.WOOL, ProductType.MILK};
    private transient GameStatus gameStatus;
    private int turns = 0;

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
        gameStatus.setTurns(turns);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Game(){

    }
    public Game(Mission mission) {
        this.mission = mission;
        gameStatus = new GameStatus(money);
    }

    public Game(Mission mission, String playerName){
        this.mission = mission;
        this.playerName = playerName;
        gameStatus = new GameStatus(money);
    }

    public Game(int money, Mission mission, String playerName) {
        this.money = money;
        this.mission = mission;
        this.playerName = playerName;
        gameStatus = new GameStatus(money);
    }

    public Game(int money, Mission mission) {
        this.money = money;
        this.mission = mission;
        gameStatus = new GameStatus(money);
    }

    public void loadMarketProducts(String path) throws IOException {
        Reader reader = new FileReader(path);
        YaGson gson = new YaGsonBuilder().create();
        marketProducts = gson.fromJson(reader, ProductType[].class);
        reader.close();
    }

    public void saveMarketProducts(String path) throws IOException {
        Writer writer = new FileWriter(path);
        YaGson gson = new YaGsonBuilder().create();
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
                increaseMoney(truck.getProductsPrice());
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
        setTurns(turns + 1);
    }

    public boolean isFinished(){
        Mission temp = new Mission(money,time,farm.getWarehouse().getProductMap(),0);
        if(temp.compareTo(mission) == 1){
            return true;
        }
        return false;
    }

    public void well() throws MoneyNotEnoughException {
        if( money >= farm.getWell().getRefillPrice()) {
            decreaseMoney(farm.getWell().getRefillPrice());
            farm.getWell().refill();
        }
        else{
            throw new MoneyNotEnoughException();
        }
    }

    public void removeProductFromVehicle(Vehicle vehicle, ProductType productType, int number) {
        HashMap <ProductType, Integer> map = new HashMap<>();
        map.put(productType, number);
        if (vehicle instanceof Truck) {
            farm.getWarehouse().addProduct(map);
        }
        vehicle.removeProduct(productType, number);
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
            decreaseMoney(number * productType.getBuyCost());
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
        gameStatus.setMoney(money);
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
            increaseMoney(vehicle.getProductsPrice());
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

    public void saveToJson(String path) throws IOException {
        try {
            Writer writer = new FileWriter(path);
            YaGson gson = new YaGsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
            gson.toJson(this,writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Game loadFromJson(String path) throws IOException {
        Reader reader = new FileReader(path);
        YaGson gson = new YaGsonBuilder().create();
        Game output = gson.fromJson(reader, Game.class);
        reader.close();
        output.gameStatus = new GameStatus(output.money,output.turns);
        return output;
    }

    public void increaseMoney(int amount) {
        money += amount;
        gameStatus.setMoney(money);
    }



}

