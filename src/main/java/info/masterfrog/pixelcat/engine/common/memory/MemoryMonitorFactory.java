package info.masterfrog.pixelcat.engine.common.memory;

public class MemoryMonitorFactory {
    private static MemoryMonitorFactory instance;
    private static MemoryMonitorUtil memoryMonitorUtil;

    private MemoryMonitorFactory() {
        // do nothing
    }

    public static MemoryMonitorFactory getInstance() {
        if (instance == null) {
            instance = new MemoryMonitorFactory();
        }

        return instance;
    }

    public MemoryMonitorUtil getMemoryMonitorUtil() {
        if (memoryMonitorUtil == null) {
            memoryMonitorUtil = new MemoryMonitorUtilImpl();
        }

        return memoryMonitorUtil;
    }
}
