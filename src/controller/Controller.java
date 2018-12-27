package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import model.exception.*;

import java.io.*;

public class Controller {
    private Game game;
    public void pickup(Point point) {
        game.getFarm().pickup(point);
    }

    public void cage(Point point) {
        game.getCell(point).cage();
    }

    public void plant(Point point) {
        game.getFarm().getMap().plant(point);
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
        Writer writer = new FileWriter(address);
        Gson gson = new GsonBuilder().create();
        gson.toJson(game, writer);
        writer.close();
    }
    public void loadGame(String address) throws IOException {
        Reader reader = new FileReader(address);
        Gson gson = new GsonBuilder().create();
        game = gson.fromJson(reader, Game.class);
        reader.close();
    }

    public void loadWorkshop(String address) throws IOException {
        game.getFarm().setCustomWorkshop(6, Workshop.readFromJson(address));
    }

    public void saveWorkshop(int number, String address) throws IOException {
        game.getFarm().getCustomWorkshop(number).saveToJson(address);
    }

    public void loadProducts(VehicleType vehicleType,ProductType productType, int number) throws VehicleOnTripException, MoneyNotEnoughException, NotEnoughCapacityException, NotEnoughItemsException {
        game.addProductToVehicle(game.getFarm().getVehicleByName(vehicleType),productType,number);
    }


}
