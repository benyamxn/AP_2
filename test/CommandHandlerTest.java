import model.Request;
import model.RequestType;
import model.exception.RequestNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import view.CommandHandler;

public class CommandHandlerTest {
    CommandHandler commandHandler = new CommandHandler();
    @Test
    public void getRequestTest() throws RequestNotFoundException {
        Request request =  commandHandler.getRequest("print levels");
        Assert.assertEquals(request.getRequestType(), RequestType.PRINT_LEVELS);
    }
    @Test
    public void getUpgradeTypeRequest(){
        RequestType requestType = commandHandler.getUpgradeType("well");
        try {
            Assert.assertEquals(requestType,RequestType.UPGRADE_WELL);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        requestType = commandHandler.getUpgradeType("cat");
        try {
            Assert.assertEquals(requestType,RequestType.UPGRADE_CAT);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
