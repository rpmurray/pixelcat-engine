package com.rpm.pixelcat.engine.exception;

public class GameException extends Exception {
    private GameErrorCode errorCode;
    private String details;
    private Object data;

    public GameException(GameErrorCode errorCode, String details, Object data, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.details = details;
        this.errorCode = errorCode;
        this.data = data;
    }

    public GameErrorCode getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }

    public Object getData() {
        return data;
    }

    protected String toStringBase() {
        return "errorCode=" + errorCode +
               ", details=\"" + details + "\"" +
               ", data=<" + data + ">" +
               ", cause=[" + getCause() + "]";
    }

    @Override
    public String toString() {
        return "GameException{" +
            toStringBase() +
            '}';
    }
}

