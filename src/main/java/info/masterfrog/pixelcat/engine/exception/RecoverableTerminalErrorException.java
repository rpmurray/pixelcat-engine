package info.masterfrog.pixelcat.engine.exception;

import java.util.Set;

public class RecoverableTerminalErrorException extends TerminalErrorException {
    public RecoverableTerminalErrorException(Set<Exception> terminalErrors) {
        super(terminalErrors);
    }
}
