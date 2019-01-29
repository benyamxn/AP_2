package model.exception;

import GUI.FarmGUI;

public class NotEnoughWaterException extends Exception{
    public NotEnoughWaterException() {
        FarmGUI.getSoundPlayer().playTrack("error");
    }
    public NotEnoughWaterException(String s) {
        super(s);
    }
}
