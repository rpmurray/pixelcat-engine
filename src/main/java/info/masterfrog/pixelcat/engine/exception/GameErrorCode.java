package info.masterfrog.pixelcat.engine.exception;

public interface GameErrorCode {
    /* Error codes below 100000 are reserved for use by the game engine, higher error codes are free for game implementation use */
    public Long getCode();

    public String getMessage();
}
