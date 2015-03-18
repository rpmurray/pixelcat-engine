package com.rpm.pixelcat.engine.exception;

public class GameException extends Exception {
    private GameErrorCode errorCode;
    private Object data;

    public GameException(GameErrorCode errorCode, Object data, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.data = data;
    }

    public GameException(GameErrorCode errorCode, Object data) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = data;
    }

    public GameException(GameErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.data = null;
    }

    public GameException(GameErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = null;
    }

    public GameErrorCode getErrorCode() {
        return errorCode;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "GameException{" +
            "errorCode=" + errorCode +
            "data=<" + data + ">" +
            '}';
    }
}

