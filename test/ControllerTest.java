import controller.Controller;
import model.Game;
import model.Mission;
import org.junit.Test;

import java.io.IOException;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ControllerTest {
    @Test
    public void testJson() {
        Controller controller = new Controller();
        try {
            controller.saveGame("gameData/savedGames/game.json");
        } catch (IOException e) {
            fail();
        }
    }
}
