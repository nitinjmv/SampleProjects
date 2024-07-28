package dev.jmv.account.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String exception){
        super(exception);
    }
}
