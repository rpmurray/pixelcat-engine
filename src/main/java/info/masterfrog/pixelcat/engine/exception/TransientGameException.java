package info.masterfrog.pixelcat.engine.exception;

public class TransientGameException extends GameException {
    public TransientGameException(GameErrorCode errorCode, String details, Object data, Throwable cause) {
        super(errorCode, details, data, cause);
    }

    public TransientGameException(GameErrorCode errorCode, Object data, Throwable cause) {
        super(errorCode, null, data, cause);
    }

    public TransientGameException(GameErrorCode errorCode, String details, Throwable cause) {
        super(errorCode, details, null, cause);
    }

    public TransientGameException(GameErrorCode errorCode, String details, Object data) {
        super(errorCode, details, data, null);
    }

    public TransientGameException(GameErrorCode errorCode, Throwable cause) {
        super(errorCode, null, null, cause);
    }

    public TransientGameException(GameErrorCode errorCode, Object data) {
        super(errorCode, null, data, null);
    }

    public TransientGameException(GameErrorCode errorCode, String details) {
        super(errorCode, details, null, null);
    }

    public TransientGameException(GameErrorCode errorCode) {
        super(errorCode, null, null, null);
    }

    @Override
    public String toString() {
        return "TransientGameException{" +
            toStringBase() +
            '}';
    }
}

