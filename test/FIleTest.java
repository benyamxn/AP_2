import org.junit.Test;

import java.nio.file.Paths;

public class FIleTest {

    @Test
    public void findPathTest(){
        System.out.println(Paths.get(System.getProperty("user.dir")).toString());
    }
}
