package view;
import model.Request;
import model.exception.RequestNotFoundException;


import java.util.Scanner;

public class View {
    private static Scanner scanner = new Scanner(System.in);
    private CommandHandler commandHandler = new CommandHandler();
    private StatusHandler statusHandler = new StatusHandler();
    public Request getRequest() throws RequestNotFoundException {
       return commandHandler.getRequest(scanner.nextLine());
    }

    public void println(String str){
        System.out.println(str);
    }

    public StatusHandler getStatusHandler() {
        return statusHandler;
    }
}
