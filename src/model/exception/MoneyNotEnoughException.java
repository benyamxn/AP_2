package model.exception;

import GUI.FarmGUI;

public class MoneyNotEnoughException extends Exception {

    public MoneyNotEnoughException() {
        FarmGUI.getSoundPlayer().playTrack("error");
    }
    public MoneyNotEnoughException(String s) {
        super(s);
    }
}
