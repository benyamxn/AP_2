package view;

import model.*;

public class StatusHandler {

    public String getStatus (Map map){
        return map.getStatus();
    }

    public String getStatus (Game game){
        return game.getStatus();
    }

    public String getStatus (Mission mission){
        return mission.getStatus();
    }

    public String getStatus (Warehouse warehouse){
        return warehouse.getStatus();
    }

    public String getStatus (Well well){
        return well.getStatus();
    }

    public String getStatus (Workshop workshop){
        return workshop.getStatus();
    }

    public String getStatus (Vehicle vehicle){
        return vehicle.getStatus();
    }

}
