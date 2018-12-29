import model.*;
import org.junit.Test;
import controller.*;

public class StatusHandlerTest {

    @Test
    public void testMapStatus(){
        Map map = new Map(3, 3);
        Point point = new Point(0, 0);
        map.getCell(point).addAnimal(new Domesticated(point, DomesticatedType.CHICKEN));

        point = new Point(2, 0);
        map.getCell(point).addProduct(new Product(ProductType.MILK));

        point = new Point(1, 1);
        map.getCell(point).addAnimal(new Wild(point, WildType.LION));
        map.getCell(point).addProduct(new Product(ProductType.CAKE));


        System.out.println(map.getStatus());
    }

    @Test
    public void testTruckStatus(){
        Truck truck = new Truck();
        truck.upgrade();
        truck.addProduct(ProductType.CAGED_LION, 3);
        truck.addProduct(ProductType.COOKIE, 5);
        truck.startTravel();
        truck.setEstimatedTimeOfArrival(2);

        System.out.println(truck.getStatus());

        Helicopter helicopter = new Helicopter();
        helicopter.addProduct(ProductType.CAGED_GRIZZLY, 4);
        helicopter.addProduct(ProductType.CAGED_LION, 2);

        System.out.println(helicopter.getStatus());
    }
}
