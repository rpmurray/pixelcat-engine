package info.masterfrog.pixelcat.engine.exception;

import java.util.Set;

public class TerminalErrorException extends GameException {
    private Set<Exception> terminalErrors;

    public TerminalErrorException(Set<Exception> terminalErrors) {
        super(GameErrorCode.TERMINAL_ERROR_CONDITION, null, null, null);
        this.terminalErrors = terminalErrors;
    }

    @Override
    public String toString() {
        return "TerminalErrorException{" +
            toStringBase() +
            ", terminalErrors=(" + terminalErrors + ")" +
            '}';
    }
}
