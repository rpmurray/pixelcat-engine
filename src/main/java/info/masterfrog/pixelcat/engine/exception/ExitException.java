package info.masterfrog.pixelcat.engine.exception;

public class ExitException extends GameException {
    public ExitException() {
        super(GameEngineErrorCode.EXIT_CONDITION, null, null, null);
    }
}
