package model.exception;

import GUI.FarmGUI;

public class VehicleOnTripException  extends Exception{

    public VehicleOnTripException() {
        FarmGUI.getSoundPlayer().playTrack("error");
    }
    public VehicleOnTripException(String s) {
        super(s);
    }

}
