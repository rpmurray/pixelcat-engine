package info.masterfrog.pixelcat.engine.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

public class TerminalErrorException extends GameException {
    private Set<Exception> terminalErrors;

    public TerminalErrorException(Set<Exception> terminalErrors) {
        super(GameEngineErrorCode.TERMINAL_ERROR_CONDITION, null, null, null);
        this.terminalErrors = terminalErrors;
    }

    @Override
    public String toString() {
        String str = "TerminalErrorException{" +
            toStringBase() +
            ", terminalErrors=(";
        String terminalErrorsStr = "";
        for (Exception e : terminalErrors) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String dump = new String(baos.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);
            terminalErrorsStr += (terminalErrorsStr.isEmpty() ? "" : ", ") + dump;
        }
        str += terminalErrorsStr + ")}";

        return str;
    }
}
