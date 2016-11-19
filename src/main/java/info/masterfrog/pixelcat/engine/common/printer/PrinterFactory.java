package info.masterfrog.pixelcat.engine.common.printer;

public class PrinterFactory {
    private static PrinterFactory instance;

    private PrinterFactory() {
        // do nothing
    }

    public static PrinterFactory getInstance() {
        if (instance == null) {
            instance = new PrinterFactory();
        }

        return instance;
    }

    public Printer createPrinter(Class context, PrinterThread thread) {
        Printer printer = new PrinterImpl(context, thread);

        return printer;
    }

    public Printer createPrinter(Class context) {
        Printer printer = new PrinterImpl(context, BasePrinterThread.DEFAULT);

        return printer;
    }
}
