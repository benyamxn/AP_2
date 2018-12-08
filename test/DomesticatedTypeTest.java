import model.DomesticatedType;
import org.junit.Assert;
import org.junit.Test;

public class DomesticatedTypeTest {

    @Test
    public void testGetTypeByString(){
        String type = "King Penguin";
        DomesticatedType animalType = DomesticatedType.getTypeByString(type);
        Assert.assertEquals(DomesticatedType.KING_PENGUIN, animalType);
    }
}
