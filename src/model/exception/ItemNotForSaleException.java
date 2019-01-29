package model.exception;

import GUI.FarmGUI;

public class ItemNotForSaleException extends Exception{
    public ItemNotForSaleException() {
        FarmGUI.getSoundPlayer().playTrack("error");
    }
    public ItemNotForSaleException(String s) {
        super(s);
    }
}
