package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import model.exception.*;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class Controller {
    Mission mission = new Mission(30000, 1000);
    private Game game = new Game(20000, mission, "tester");
    private LinkedList<Farm> farms = new LinkedList<>();
    public void pickup(Point point) throws NotEnoughCapacityException {
        game.getFarm().pickup(point);
    }

    public void cage(Point point) {
        game.getCell(point).cage();
    }

    public void plant(Point point) throws NotEnoughWaterException {
        game.getFarm().plant(point);
    }

    public void well() throws MoneyNotEnoughException {
        game.well();
    }

    public void startWorkshop(String name) throws NameNotFoundException {
        Farm farm = game.getFarm();
        farm.startWorkshop(farm.getWorkshopByName(name));
    }

    public void turn(int number) {
        for (int i = 0; i < number; i++) {
            game.updateGame();
        }
    }

    public void buyAnimal(String name) throws MoneyNotEnoughException, NameNotFoundException {
        Object temp = null;
        if (name.toLowerCase().trim().equals("dog")) {
            temp = new Dog(new Point(0, 0));
        } else if (name.toLowerCase().trim().equals("cat")) {
            temp = new Cat(new Point(0, 0));
        } else {
            temp = DomesticatedType.getTypeByString(name);
        }
        int totalMoney = game.getMoney();
        int price = -1;
        if (temp == null) {
            throw new NameNotFoundException("animal");
        } else {
            if (temp instanceof Dog) {
                price = ((Dog) temp).getBuyPrice();
            } else if ((temp instanceof Cat)) {
                price = ((Cat) temp).getBuyPrice();
            } else if (temp instanceof DomesticatedType) {
                price = ((DomesticatedType) temp).getBuyPrice();
            }
            if (price <= totalMoney) {
                game.decreaseMoney(price);
                if (temp instanceof Dog) {
                    game.getFarm().placeAnimal(((Dog) temp));
                } else if ((temp instanceof Cat)) {
                    game.getFarm().placeAnimal(((Cat) temp));
                } else if (temp instanceof DomesticatedType) {
                    game.getFarm().placeAnimal(((DomesticatedType) temp));
                }
            } else
                throw new MoneyNotEnoughException();
        }
    }

    public void saveGame(String address) throws IOException {
        saveObject(address, game);
    }
    public void loadGame(String address) throws IOException {
        game = ((Game) getObjectFromJson(address, Game.class));
    }

    public void loadWorkshop(String address) throws IOException {
        game.getFarm().setCustomWorkshop(6, Workshop.readFromJson(address));
    }

    public void saveWorkshop(int number, String address) throws IOException {
        game.getFarm().getCustomWorkshop(number).saveToJson(address);
    }

    public void loadProducts(VehicleType vehicleType,ProductType productType, int number) throws VehicleOnTripException, MoneyNotEnoughException, NotEnoughCapacityException, NotEnoughItemsException, ItemNotForSaleException {
        game.addProductToVehicle(game.getFarm().getVehicleByName(vehicleType),productType,number);
    }

    public void clear(VehicleType vehicleType) throws VehicleOnTripException, NotEnoughCapacityException {
        game.clear(vehicleType);
    }

    public void goVehicle(VehicleType vehicleType) throws VehicleOnTripException {
        Vehicle vehicle = game.getFarm().getVehicleByName(vehicleType);
        if (vehicle.isOnTravel()) {
            throw new VehicleOnTripException();
        }
        vehicle.startTravel();
    }

    public void loadMission(String path) throws IOException {
        game.setMission(Mission.loadFromJson(path));
    }

    public void saveMission(String path) throws IOException {
        game.getMission().saveToJson(path);
    }

    public void loadCustom(String path) throws IOException {
        File dir = new File(path);
        File[] directoryListing = dir.listFiles();
        if(directoryListing != null){
            for (File file : directoryListing) {
               farms.add((Farm) getObjectFromJson(file.getPath(),Farm.class));
            }
        }
    }

    public void saveCustom(String path) throws IOException {
        saveObject(path, game.getFarm());
    }

    public Object getObjectFromJson(String path, Class temp) throws IOException {
        Reader reader = new FileReader(path);
        Gson gson = new GsonBuilder().create();
        Object output = gson.fromJson(reader, temp);
        reader.close();
        return output;

    }

    public void saveObject(String path, Object object) throws IOException {
        Writer writer = new FileWriter(path);
        Gson gson = new GsonBuilder().create();
        gson.toJson(object, writer);
        writer.close();
    }

    public void upgrade(Upgradable upgradable) throws MoneyNotEnoughException, MaxLevelException {
       if(upgradable.canUpgrade()){
           if(game.getMoney() < upgradable.getUpgradePrice()){
               throw new MoneyNotEnoughException();
           }
           game.decreaseMoney(upgradable.getUpgradePrice());
           upgradable.upgrade();
           return;
       }
       throw new MaxLevelException();
    }

    public void runMap(String name){
        for (Farm farm : farms) {
            if(name.equals(farm.getName())){
                game.setFarm(farm);
                return;
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public LinkedList<Farm> getFarms() {
        return farms;
    }
}
