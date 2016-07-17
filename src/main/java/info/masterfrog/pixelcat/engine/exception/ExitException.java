package info.masterfrog.pixelcat.engine.exception;

public class ExitException extends GameException {
    public ExitException() {
        super(GameErrorCode.EXIT_CONDITION, null, null, null);
    }
}
