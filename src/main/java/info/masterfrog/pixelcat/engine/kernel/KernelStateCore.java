package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.TerminalErrorException;

import java.util.HashMap;

interface KernelStateCore {
    void init(HashMap<KernelStateProperty, Object> initProperties) throws TerminalErrorException;

    void sustainHIDEvents();

    void resetTransientHIDEvents();

    static KernelStateCore getInstance() {
        return KernelStateImpl.getInstance();
    }
}
