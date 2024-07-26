package dev.jmv.basic.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String exception){
        super(exception);
    }
}
