package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

public interface KernelInjection {
    public void run() throws TransientGameException;
}
