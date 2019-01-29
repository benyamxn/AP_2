package model.exception;

import GUI.FarmGUI;

public class MaxLevelException extends Exception {
    public MaxLevelException() {
        FarmGUI.getSoundPlayer().playTrack("error");
    }
    public MaxLevelException(String s){
        super(s);
    }
}
