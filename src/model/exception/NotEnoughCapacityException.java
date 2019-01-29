package model.exception;

import GUI.FarmGUI;

public class NotEnoughCapacityException extends  Exception{

    public NotEnoughCapacityException() {
        FarmGUI.getSoundPlayer().playTrack("error");
    }

}
