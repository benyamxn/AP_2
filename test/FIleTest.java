
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import com.gilecode.yagson.com.google.gson.Gson;
import com.gilecode.yagson.com.google.gson.GsonBuilder;
import com.gilecode.yagson.com.google.gson.internal.LinkedTreeMap;
import model.Mission;
import model.ProductType;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;

public class FIleTest {

    @Test
    public void findPathTest(){
        System.out.println(Paths.get(System.getProperty("user.dir")).toString());
    }

    @Test
    public void produceMission(){

        Path path = Paths.get(System.getProperty("user.dir"),"gameData","savedMissions","missions.json");
        ArrayList<Mission> arrayList = new ArrayList<>();

        EnumMap<ProductType,Integer> map = new EnumMap<ProductType,Integer>(ProductType.class);
        map.put(ProductType.EGG,5);

        Mission mission1 = new Mission(500,500,map,1);
        map = map.clone();
        map.put(ProductType.EGG,5);
        map.put(ProductType.DRIED_EGG,3);
        Mission mission2 = new Mission(1000,1000,map,2);
        map.put(ProductType.EGG,10);
        map.put(ProductType.DRIED_EGG,10);
        map = map.clone();
        map.put(ProductType.EGG,15);

        Mission mission3 = new Mission(1000,1500,map,3);
        map = map.clone();
        map.put(ProductType.EGG,20);
        Mission mission4 = new Mission(1000,2000,map,4);
        arrayList.addAll(Arrays.asList(mission1,mission2,mission3,mission4));
        try {
            OutputStream outputStream = new FileOutputStream(path.toString()) ;
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path.toString()));
            objectOutputStream.writeObject(arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream input = new FileInputStream(path.toString());
            ObjectInputStream inputStream = new ObjectInputStream(input);
            ArrayList missions = (ArrayList) inputStream.readObject();
            for (Object o : missions) {
                System.out.println(((Mission) o).getMoneyGoal());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

