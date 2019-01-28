import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.sun.jdi.IntegerValue;
import model.Mission;
import model.ProductType;
import org.junit.Test;

import java.io.*;
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

        Mission mission1 = new Mission(500,500,map);
        map = map.clone();
        map.put(ProductType.EGG,5);
        map.put(ProductType.DRIED_EGG,3);
        Mission mission2 = new Mission(1000,1000,map);
        arrayList.add(mission1);

        map.put(ProductType.EGG,10);
        map.put(ProductType.DRIED_EGG,10);
        map = map.clone();
        map.put(ProductType.EGG,15);

        Mission mission3 = new Mission(1000,1500,map);
        map = map.clone();
        map.put(ProductType.EGG,20);
        Mission mission4 = new Mission(1000,2000,map);


        arrayList.addAll(Arrays.asList(mission1,mission2,mission3,mission3,mission4));
        try {
            Writer writer = new FileWriter(path.toString());
            YaGson gson = new YaGsonBuilder().create();
            gson.toJson(arrayList, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Reader reader = new FileReader(path.toString());
            YaGson gson = new YaGsonBuilder().create();
            ArrayList<Mission> missions = gson.fromJson(reader, ArrayList.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
