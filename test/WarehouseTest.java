import model.Warehouse;
import org.junit.Test;

public class WarehouseTest {

    @Test
    public void testCapacity(){
        Warehouse warehouse = new Warehouse();
        for (int i = 1; i <= 5; i++) {
            System.out.println(warehouse.getTotalCapacity(i));
        }
    }
}
