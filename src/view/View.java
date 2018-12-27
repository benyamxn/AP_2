package view;
import model.Request;


import java.util.Scanner;

public class View {
    private Scanner scanner = new Scanner(System.in);
    private CommandHandler commandHandler = new CommandHandler();

    public Request getRequest(){
       return commandHandler.getRequest(scanner.nextLine());
    }

    public void println(String str){
        System.out.println(str);
    }

}
