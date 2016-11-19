package info.masterfrog.pixelcat.engine.kernel;

import info.masterfrog.pixelcat.engine.exception.TransientGameException;

interface CanvasManagerFactory {
    static CanvasManager create() throws TransientGameException {
        // instantiate
        CanvasManagerImpl canvasLibraryImpl = new CanvasManagerImpl();

        return canvasLibraryImpl;
    }
}
