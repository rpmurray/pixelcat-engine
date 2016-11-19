package info.masterfrog.pixelcat.engine.common.printer;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;


public class PrinterImpl implements Printer {
    private Logger logger;
    private Class context;
    private PrinterThread thread;

    PrinterImpl(Class context, PrinterThread thread) {
        this.context = context;
        this.thread = thread;
        logger = Logger.getLogger(context);
        logger.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
        logger.setAdditivity(false);
    }


    public void printError(Throwable throwable) {
        logger.error(printThread(), throwable);
    }

    public void printWarning(Object warning) {
        if (PrinterUtil.isThreadHidden(thread)) {
            return;
        }
        logger.warn(printThread() + warning);
    }

    public void printInfo(Object info) {
        if (PrinterUtil.isThreadHidden(thread)) {
            return;
        }
        logger.info(printThread() + info);
    }

    public void printDebug(Object debug) {
        if (PrinterUtil.isThreadHidden(thread)) {
            return;
        }
        logger.debug(printThread() + debug);
    }

    public void printTrace(Object trace) {
        if (PrinterUtil.isThreadHidden(thread)) {
            return;
        }
        logger.trace(printThread() + trace);
    }

    private String printThread() {
        return "[:" + thread.name() + ":] ";
    }
}
