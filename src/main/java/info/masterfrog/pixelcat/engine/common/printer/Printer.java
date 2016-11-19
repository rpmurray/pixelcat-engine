package info.masterfrog.pixelcat.engine.common.printer;

import org.apache.log4j.Level;

public interface Printer {
    public void printError(Throwable throwable);

    public void printWarning(Object warning);

    public void printInfo(Object info);

    public void printDebug(Object debug);

    public void printTrace(Object trace);

    public static void setLevel(Level level) {
        PrinterUtil.setLevel(level);
    }

    public static Class<Level> getLogLevelClass() {
        return PrinterUtil.getLogLevelClass();
    }

    public static Level getLogLevelFatal() {
        return PrinterUtil.getLogLevelFatal();
    }

    public static Level getLogLevelError() {
        return PrinterUtil.getLogLevelError();
    }

    public static Level getLogLevelWarn() {
        return PrinterUtil.getLogLevelWarn();
    }

    public static Level getLogLevelInfo() {
        return PrinterUtil.getLogLevelInfo();
    }

    public static Level getLogLevelDebug() {
        return PrinterUtil.getLogLevelDebug();
    }

    public static Level getLogLevelTrace() {
        return PrinterUtil.getLogLevelTrace();
    }

    public static void hideThread(PrinterThread t) {
        PrinterUtil.hideThread(t);
    }

    public static void showThread(PrinterThread t) {
        PrinterUtil.showThread(t);
    }

    public static Boolean isThreadHidden(PrinterThread t) {
        return PrinterUtil.isThreadHidden(t);
    }
}
