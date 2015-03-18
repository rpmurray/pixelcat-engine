package com.rpm.pixelcat.engine.kernel;

public class KernelFactory {
    private static KernelFactory instance;

    private KernelFactory() {
        // do nothing
    }

    public static KernelFactory getInstance() {
        if (instance == null) {
            instance = new KernelFactory();
        }

        return instance;
    }

    public Kernel createKernel() {
        Kernel kernel = new KernelImpl();

        return kernel;
    }
}
