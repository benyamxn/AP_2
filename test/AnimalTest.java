import org.junit.Assert;
import org.junit.Test;

public class AnimalTest {

    @Test
    public void testMoveUpRight(){
        Point point = new Point(20, 34);
        Point animalLocation = new Point(14, 20);
        Animal animal = new Domesticated(animalLocation);
        animal.moveToPoint(point);
        Assert.assertEquals(new Point(15, 21), animal.getLocation());
    }

    @Test
    public void testMoveLeft(){
        Point point = new Point(20, 34);
        Point animalLocation = new Point(14, 20);
        Animal animal = new Domesticated(animalLocation);
        point = new Point(11, 20);
        animal.moveToPoint(point);
        Assert.assertEquals(new Point(13, 20), animal.getLocation());
    }


}
