package model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
public class Mission implements  Comparable<Mission> {

    private int moneyGoal;
    private int timeGoal;
    private transient EnumMap<ProductType,Integer> productsGoal = new EnumMap<>(ProductType.class);
    private LinkedList<ProductType> productGoalSpare = new LinkedList<>();


    public Mission(int moneyGoal, int timeGoal) {
        this.moneyGoal = moneyGoal;
        this.timeGoal = timeGoal;
        productsGoal.put(ProductType.EGG, 10);
        productsGoal.put(ProductType.DRIED_EGG, 5);
    }

    public Mission(int moneyGoal, int timeGoal, EnumMap<ProductType,Integer> productsGoal ){
        this.moneyGoal = moneyGoal;
        this.timeGoal = timeGoal;
        this.productsGoal = productsGoal;
    }

    public int getMoneyGoal() {
        return moneyGoal;
    }

    public int getTimeGoal() {
        return timeGoal;
    }

    public Map<ProductType, Integer> getProductsGoal() {
        return productsGoal;
    }

    public Map<ProductType,Integer> productsGoal(){
        return productsGoal;
    }

    @Override
    public int compareTo(Mission second){
        if(this.getTimeGoal() <= second.getTimeGoal() ){
            if(this.getMoneyGoal() >= second.getMoneyGoal()){
                for (Map.Entry<ProductType, Integer> entry : productsGoal.entrySet()){
                    if(entry.getValue() < second.productsGoal.get(entry.getKey())){
                        return 0;
                    }
                }
                return 1;
            }
        }
        return 0;
    }

    public void saveToJson(String path) throws IOException {
        for (Map.Entry<ProductType, Integer> productTypeIntegerEntry : productsGoal.entrySet()) {
            for (Integer i = 0; i < productTypeIntegerEntry.getValue(); i++) {
                productGoalSpare.add(productTypeIntegerEntry.getKey());
            }
        }
        Writer writer = new FileWriter(path);
        Gson gson = new GsonBuilder().create();
        gson.toJson(this, writer);
        writer.close();
    }

    public static Mission loadFromJson(String path) throws IOException {
        Reader reader = new FileReader(path);
        Gson gson = new GsonBuilder().create();
        Mission mission = gson.fromJson(reader, Mission.class);
        reader.close();
        mission.productsGoal = new EnumMap<>(ProductType.class);
        for (ProductType productType : mission.productGoalSpare) {
            if(mission.productsGoal.containsKey(productType))
                mission.productsGoal.put(productType,mission.productsGoal.get(productType) + 1);
            else
                mission.productsGoal.put(productType,1);
        }
        return mission;
    }

    public String getStatus() {
        String status = "Mission Goals:\n" + "Time Goal: " + timeGoal + "\n" + "Money Goal: " + moneyGoal + "\n";
        for (Map.Entry<ProductType, Integer> entry : productsGoal.entrySet()){
            status += entry.getKey().toString() + " Goal: " + entry.getValue() + "\n";
        }
        return status;
    }
}
