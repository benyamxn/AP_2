package model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
public class Mission implements  Comparable<Mission> {

    private int moneyGoal;
    private int timeGoal;
    private  Map<ProductType,Integer>  productsGoal = new HashMap<>();

    public Mission(int moneyGoal, int timeGoal) {
        this.moneyGoal = moneyGoal;
        this.timeGoal = timeGoal;
        productsGoal.put(ProductType.EGG, 10);
        productsGoal.put(ProductType.DRIED_EGG, 5);
    }

    public Mission(int moneyGoal, int timeGoal, Map productsGoal ){
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
