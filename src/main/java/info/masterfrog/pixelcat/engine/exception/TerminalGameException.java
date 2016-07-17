package info.masterfrog.pixelcat.engine.exception;

public class TerminalGameException extends GameException {
    public TerminalGameException(GameException e) {
        super(e.getErrorCode(), e.getDetails(), e.getData(), e.getCause());
    }
    public TerminalGameException(GameErrorCode errorCode, String details, Object data, Throwable cause) {
        super(errorCode, details, data, cause);
    }

    public TerminalGameException(GameErrorCode errorCode, Object data, Throwable cause) {
        super(errorCode, null, data, cause);
    }

    public TerminalGameException(GameErrorCode errorCode, String details, Throwable cause) {
        super(errorCode, details, null, cause);
    }

    public TerminalGameException(GameErrorCode errorCode, String details, Object data) {
        super(errorCode, details, data, null);
    }

    public TerminalGameException(GameErrorCode errorCode, Throwable cause) {
        super(errorCode, null, null, cause);
    }

    public TerminalGameException(GameErrorCode errorCode, Object data) {
        super(errorCode, null, data, null);
    }

    public TerminalGameException(GameErrorCode errorCode, String details) {
        super(errorCode, details, null, null);
    }

    public TerminalGameException(GameErrorCode errorCode) {
        super(errorCode, null, null, null);
    }

    @Override
    public String toString() {
        return "TerminalGameException{" +
            toStringBase() +
            '}';
    }
}

