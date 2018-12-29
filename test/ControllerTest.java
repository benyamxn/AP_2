import controller.Controller;
import model.Game;
import model.Mission;
import org.junit.Test;

import java.io.IOException;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ControllerTest {
    @Test
    public void testSaveGame() {
        Controller controller = new Controller();
        try {
            controller.saveGame("gameData/savedGames/game.json");
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
        try {
            controller.saveMission("gameData/savedMissions/mission1.json");
        } catch (IOException e) {
            fail();
        }
    }
}
