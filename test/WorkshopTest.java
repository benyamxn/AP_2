import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Point;
import model.Workshop;
import model.WorkshopType;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class WorkshopTest {
    @Test
    public void testJson() {
        Workshop workshop = new Workshop(WorkshopType.EGG_POWERDER_PLANT, new Point(0,0));
        workshop.upgrade();
        try {
            workshop.saveToJson("test.json");
            Gson gson = new GsonBuilder().create();
            Workshop newWorkshop = Workshop.readFromJson("test.json");
            assertEquals(workshop, newWorkshop);
        } catch (IOException e) {
            fail();
        }
    }
}
