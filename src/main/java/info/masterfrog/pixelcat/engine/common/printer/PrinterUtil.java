package info.masterfrog.pixelcat.engine.common.printer;

import org.apache.log4j.Level;

import java.util.HashSet;
import java.util.Set;

class PrinterUtil {
    private static Set<Class> hiddenThreads = new HashSet<>();

    static void setLevel(Level level) {
        System.out.print("--- [main] MESSAGE " + PrinterImpl.class.getName() + "  - Log level changed to " + level + "\n");
        org.apache.log4j.Logger.getRootLogger().setLevel(level);
    }

    static Class<Level> getLogLevelClass() {
        return Level.class;
    }

    static Level getLogLevelFatal() {
        return Level.FATAL;
    }

    static Level getLogLevelError() {
        return Level.ERROR;
    }

    static Level getLogLevelWarn() {
        return Level.WARN;
    }

    static Level getLogLevelInfo() {
        return Level.INFO;
    }

    static Level getLogLevelDebug() {
        return Level.DEBUG;
    }

    static Level getLogLevelTrace() {
        return Level.TRACE;
    }

    static void hideThread(Class c) {
        hiddenThreads.add(c);
    }

    static void showThread(Class c) {
        if (hiddenThreads.contains(c)) {
            hiddenThreads.remove(c);
        }
    }

    static Boolean isThreadHidden(Class c) {
        return hiddenThreads.contains(c);
    }
}
