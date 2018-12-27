package view;
import model.Request;
import model.RequestType;
import model.VehicleType;
public class CommandHandler {

    private static final String BUY = "buy (.*)";
    private static final String PICKUP = "pick up (\\d+) (\\d+)";
    private static final String CAGE =  "cage (\\d+) (\\d+)";
    private static final String PLANT = "plant (\\d+) (\\d+)";
    private static final String WELL = "well";
    private static final String START = "start (.*)";
    private static final String  UPGRADE = "upgrade (.*)";
    private static final String LOAD_CUSTOM = "load custom (.*)";
    private static final String RUN = "run (.*)";
    private static final String SAVE_GAME = "save game (.*)";
    private static final String LOAD_GAME = "load game (.*)";
    private static final String PRINT = "print (.*)";
    private static final String TURN = "turn (\\d+)";
    private static final String ADD = "(.*) add (.*) (\\d+)";
    private static final String CLEAR = "(.*) clear";
    private static final String GO = "(.*) go";


    public  Request getRequest(String command){
        String[] parameter = command.split(" ");

        if(command.matches(BUY)){
            return  new Request(RequestType.BUY_ANIMAL,Integer.parseInt(parameter[1]));
        }
        else if(command.matches(PICKUP)){
            return  new Request(RequestType.PICKUP,Integer.parseInt(parameter[1]) , Integer.parseInt(parameter[2]));
        }
        else if(command.matches(CAGE)){
            return  new Request(RequestType.CAGE,Integer.parseInt(parameter[1]) , Integer.parseInt(parameter[2]));
        }
        else if(command.matches(PLANT)){
            return  new Request(RequestType.PLANT,Integer.parseInt(parameter[1]) , Integer.parseInt(parameter[2]));
        }
        else if(command.matches(WELL)){
            return  new Request(RequestType.WELL);
        }
        else if(command.matches(START)){
            return  new Request(RequestType.START,parameter[1]);
        }
        else if(command.matches(UPGRADE)){
            RequestType temp = getUpgradeType(parameter[1]);
            if(temp == RequestType.UPGRADE_VEHICLE){
                return new Request(temp,VehicleType.valueOf(parameter[1].toUpperCase()));
            }
            else if(temp == RequestType.UPGRADE_WORKSHOP) {
                return new Request(temp, parameter[1]);
            }
            return new Request(temp);
        }
        else if(command.matches(LOAD_CUSTOM)){
            return new Request(RequestType.LOAD_CUSTOM,parameter[1]);
        }
        else if(command.matches(LOAD_GAME)){
            return new Request(RequestType.LOAD_GAME,parameter[1]);
        }
        else if(command.matches(RUN)){
            return new Request(RequestType.RUN,parameter[1]);
        }
        else if(command.matches(SAVE_GAME)){
            return new Request(RequestType.SAVE_GAME,parameter[1]);
        }
        else if(command.matches(PRINT)){
            String temp = "PRINT_";
            return new Request(RequestType.valueOf(temp.concat(parameter[1]).toUpperCase()));
        }
        else if(command.matches(TURN)){
            return  new Request(RequestType.TURN,Integer.parseInt(parameter[1]));
        }
        else if(command.matches(ADD)){
            return  new Request(RequestType.ADD_ITEM_TO_VEHICLE,parameter[2],Integer.parseInt(parameter[3])
                                                                            ,VehicleType.valueOf(parameter[0].toUpperCase()));
        }
        else if(command.matches(CLEAR)){
            return new Request(RequestType.CLEAR_VEHICLE, VehicleType.valueOf(parameter[0].toUpperCase()));
        }
        else if(command.matches(GO)){
            return new Request(RequestType.VEHICLE_GO, VehicleType.valueOf(parameter[0].toUpperCase()));
        }
        return null;
    }


    public  RequestType getUpgradeType(String parameter){
        switch (parameter.toLowerCase()){
            case "cat":
                return RequestType.UPGRADE_CAT;
            case "well":
                return RequestType.UPGRADE_WELL;
            case "warehouse":
                return RequestType.UPGRADE_WAREHOUSE;
            case "truck":
                return RequestType.UPGRADE_VEHICLE;
            case "helicopter":
                return RequestType.UPGRADE_VEHICLE;
            default:
                return RequestType.UPGRADE_WORKSHOP;
        }
    }
}
