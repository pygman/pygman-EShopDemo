package com.pygman.exception;

/**
 * Created by pygmalion on 2014/11/2.
 */
public class EbStockException extends RuntimeException {
    public EbStockException(){
        super();
    }
    public EbStockException(String message,Throwable cause){
        super(message,cause);
    }
    public EbStockException(String message){
        super(message);
    }
    public EbStockException(Throwable cause){
        super(cause);
    }
}
