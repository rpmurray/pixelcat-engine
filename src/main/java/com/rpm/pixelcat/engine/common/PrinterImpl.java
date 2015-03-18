package com.rpm.pixelcat.engine.common;

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

    public void printWarning(String warning) {
        logger.warn(warning);
    }

    public void printInfo(String info) {
        logger.info(info);
    }

    public void printDebug(String debug) {
        logger.debug(debug);
    }

    public void printTrace(String trace) {
        logger.trace(trace);
    }
}
