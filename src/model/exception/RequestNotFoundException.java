package model.exception;

public class RequestNotFoundException extends  Exception {

    public RequestNotFoundException() {
    }
    public RequestNotFoundException(String s){
        super(s);
    }
}
