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
        Workshop workshop1 = new Workshop(WorkshopType.EGG_POWDER_PLANT, new Point(0, 0));
        Workshop workshop2 = new Workshop(WorkshopType.WEAVING_FACTORY, new Point(10, 10));
        Workshop workshop3 = new Workshop(WorkshopType.SPINNERY, new Point(20, 20));
        Workshop workshop4 = new Workshop(WorkshopType.MANUFACTURING_PLANT, new Point(10, 20));
        Workshop workshop5 = new Workshop(WorkshopType.COOKIE_BAKERY, new Point(20, 10));
        Workshop workshop6 = new Workshop(WorkshopType.SOUR_CREAM, new Point(20, 0));
        try {
            workshop1.saveToJson("gameData/customWorkshops/workshop1.json");
            Workshop newWorkshop1 = Workshop.readFromJson("gameData/customWorkshops/workshop1.json");
            assertEquals(workshop1, newWorkshop1);
            workshop2.saveToJson("gameData/customWorkshops/workshop2.json");
            Workshop newWorkshop2 = Workshop.readFromJson("gameData/customWorkshops/workshop2.json");
            assertEquals(workshop2, newWorkshop2);
            workshop3.saveToJson("gameData/customWorkshops/workshop3.json");
            Workshop newWorkshop3 = Workshop.readFromJson("gameData/customWorkshops/workshop3.json");
            assertEquals(workshop3, newWorkshop3);
            workshop4.saveToJson("gameData/customWorkshops/workshop4.json");
            Workshop newWorkshop4 = Workshop.readFromJson("gameData/customWorkshops/workshop4.json");
            assertEquals(workshop4, newWorkshop4);
            workshop5.saveToJson("gameData/customWorkshops/workshop5.json");
            Workshop newWorkshop5 = Workshop.readFromJson("gameData/customWorkshops/workshop5.json");
            assertEquals(workshop5, newWorkshop5);
            workshop6.saveToJson("gameData/customWorkshops/workshop6.json");
            Workshop newWorkshop6 = Workshop.readFromJson("gameData/customWorkshops/workshop6.json");
            assertEquals(workshop6, newWorkshop6);
            assertEquals(Workshop.readFromJson("gameData/customWorkshops/workshop7.json").getType(), WorkshopType.CUSTOM);
        } catch (IOException e) {
            fail();
        }
    }
}
