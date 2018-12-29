package model.exception;

public class ItemNotForSaleException extends Exception{
    public ItemNotForSaleException() {
    }
    public ItemNotForSaleException(String s) {
        super(s);
    }
}
