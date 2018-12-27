package model.exception;

public class MoneyNotEnoughException extends RuntimeException {

    public MoneyNotEnoughException() { }
    public MoneyNotEnoughException(String s) {
        super(s);
    }
}
