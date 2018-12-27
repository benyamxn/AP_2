import model.*;
import org.junit.Test;
import controller.*;

public class StatusHandlerTest {

    @Test
    public void testMapStatus(){
        Map map = new Map(3, 3);
        Cell[][] cells = new Cell[3][3];
        cells[0][0].addAnimal(new Domesticated(cells[0][0].getCoordinate(), DomesticatedType.CHICKEN));
        cells[2][0].addProduct(new Product(ProductType.MILK));
        cells[1][1].addAnimal(new Wild(cells[1][1].getCoordinate(), WildType.LION));
        cells[1][1].addProduct(new Product(ProductType.CAKE));
        map.setCells(cells);

        System.out.println(map.getStatus());
    }

    @Test
    public void testTruckStatus(){
        Truck truck = new Truck();
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
