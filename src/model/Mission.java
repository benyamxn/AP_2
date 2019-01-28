package model;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;


import java.io.*;
import java.util.*;
import java.util.Map;

public class Mission implements  Comparable<Mission>, Serializable {

    private  int moneyGoal;
    private  int timeGoal;
    private  EnumMap<ProductType,Integer> productsGoal = new EnumMap<>(ProductType.class);

    private transient static ArrayList<Mission> missions;

    static{
//        Path path = Paths.get(System.getProperty("user.dir"),"gameData","savedMissions","missions.json");
//        try {
//            Reader reader = new FileReader(path.toString());
//            Gson gson = new GsonBuilder().create();
//            missions = gson.fromJson(reader, ArrayList.class);
//            System.out.println(missions.get(0).getProductsGoal() + " sdfsdfs" );
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public Mission(int moneyGoal, int timeGoal) {
        this.moneyGoal = moneyGoal;
        this.timeGoal = timeGoal;
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

        Writer writer = new FileWriter(path);
        YaGson gson = new YaGsonBuilder().create();
        gson.toJson(this, writer);
        writer.close();
    }

    public static Mission loadFromJson(String path) throws IOException {
        Reader reader = new FileReader(path);
        YaGson gson = new YaGsonBuilder().create();
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

    public static ArrayList<Mission> getMissions() {
        return missions;
    }
}
