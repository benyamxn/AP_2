package model;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

public class Mission implements  Comparable<Mission>, Serializable {

    private  int moneyGoal;
    private  int timeGoal;
    private int level;
    private  EnumMap<ProductType,Integer> productsGoal = new EnumMap<>(ProductType.class);

    private  static ArrayList<Mission> missions = new ArrayList<>();

    static{
        try {
            Path path = Paths.get(System.getProperty("user.dir"),"gameData","savedMissions","missions.json");
            InputStream inputStream = new FileInputStream(path.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ArrayList input = (ArrayList) objectInputStream.readObject();
            for (Object o : input) {
                missions.add((Mission) o);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Mission(int moneyGoal, int timeGoal) {
        this.moneyGoal = moneyGoal;
        this.timeGoal = timeGoal;
    }

    public Mission(int moneyGoal, int timeGoal, EnumMap<ProductType,Integer> productsGoal){
        this(moneyGoal,timeGoal);
        this.productsGoal = productsGoal;
        this.level= level;
    }

    public Mission(int moneyGoal, int timeGoal, EnumMap<ProductType,Integer> productsGoal , int level ){
        this(moneyGoal,timeGoal,productsGoal);
        this.level= level;
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
                for (Map.Entry<ProductType, Integer> entry : second.productsGoal.entrySet()){
                    if(productsGoal.get(entry.getKey()) == null || entry.getValue() > productsGoal.get(entry.getKey())){
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

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
