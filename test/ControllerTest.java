import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.Controller;
import model.Game;
import model.Mission;
import model.ProductType;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ControllerTest {
    @Test
    public void testSaveGame() {
        Controller controller = new Controller();
        try {
            controller.saveGame("gameData/savedGames/game2.json");
            controller.loadGame("gameData/savedGames/game2.json");
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testSaveFarm() {
        Controller controller = new Controller();
        try {
            controller.saveCustom("gameData/savedMaps/map1.json");
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testSaveMission() {
        Controller controller = new Controller();
        controller.getGame().setMission(new Mission(20000, 360, new EnumMap<>(ProductType.class)));
        try {
            controller.saveMission("gameData/savedMissions/mission1.json");
            controller.loadMission("gameData/savedMissions/mission1.json");
        } catch (IOException e) {
            fail();
        }
    }

}
