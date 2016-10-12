package info.masterfrog.pixelcat.engine.common.printer;

import org.apache.log4j.*;


public class PrinterImpl implements Printer {
    private Logger logger;
    private Class context;

    PrinterImpl(Class context) {
        this.context = context;
        logger = Logger.getLogger(context);
        logger.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
        logger.setAdditivity(false);
    }

    public void printError(Throwable throwable) {
        logger.error("", throwable);
    }

    public void printWarning(Object warning) {
        if (PrinterUtil.isThreadHidden(context)) {
            return;
        }
        logger.warn(warning);
    }

    public void printInfo(Object info) {
        if (PrinterUtil.isThreadHidden(context)) {
            return;
        }
        logger.info(info);
    }

    public void printDebug(Object debug) {
        if (PrinterUtil.isThreadHidden(context)) {
            return;
        }
        logger.debug(debug);
    }

    public void printTrace(Object trace) {
        if (PrinterUtil.isThreadHidden(context)) {
            return;
        }
        logger.trace(trace);
    }
}
