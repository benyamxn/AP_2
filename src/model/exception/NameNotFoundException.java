package model.exception;

public class NameNotFoundException extends RuntimeException {
    public NameNotFoundException() {
    }
    public NameNotFoundException(String s) {
        super(s);
    }

}
