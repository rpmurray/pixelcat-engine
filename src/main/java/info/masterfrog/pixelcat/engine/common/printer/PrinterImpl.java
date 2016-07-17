package info.masterfrog.pixelcat.engine.common.printer;

import org.apache.log4j.*;


public class PrinterImpl implements Printer {
    private Logger logger;

    PrinterImpl(Class context) {
        logger = Logger.getLogger(context);
        logger.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
        logger.setAdditivity(false);
    }

    public void printError(Throwable throwable) {
        logger.error("", throwable);
    }

    public void printWarning(Object warning) {
        logger.warn(warning);
    }

    public void printInfo(Object info) {
        logger.info(info);
    }

    public void printDebug(Object debug) {
        logger.debug(debug);
    }

    public void printTrace(Object trace) {
        logger.trace(trace);
    }
}
