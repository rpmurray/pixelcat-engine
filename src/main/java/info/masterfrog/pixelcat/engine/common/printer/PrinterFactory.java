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

    public Printer createPrinter(Class context) {
        Printer printer = new PrinterImpl(context);

        return printer;
    }
}
