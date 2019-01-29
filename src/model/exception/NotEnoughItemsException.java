package model.exception;

import GUI.FarmGUI;

public class NotEnoughItemsException extends Exception {

    public NotEnoughItemsException() {
        FarmGUI.getSoundPlayer().playTrack("error");
    }
    public NotEnoughItemsException(String s) {
        super(s);
    }

}
