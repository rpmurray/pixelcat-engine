package com.rpm.pixelcat.engine.test;

import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.kernel.KernelInjection;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.kernel.KernelStatePropertyEnum;

public class PreProcessingKernelInjection implements KernelInjection {
    private LevelHandler levelHandler;
    private GameObjectsHandler gameObjectsHandler;

    private static final Printer PRINTER = PrinterFactory.getInstance().createPrinter(PreProcessingKernelInjection.class);

    PreProcessingKernelInjection(LevelHandler levelHandler, GameObjectsHandler gameObjectsHandler) {
        this.levelHandler = levelHandler;
        this.gameObjectsHandler = gameObjectsHandler;
    }

    public void run(KernelState kernelState) throws GameException {
        PRINTER.printTrace("Test kernel-injected pre-processor started...");

        // handle exit trigger
        if (kernelState.hasHIDEvent(HIDEventEnum.BACK)) {
            kernelState.setProperty(KernelStatePropertyEnum.EXIT_SIGNAL, true);
        }

        // level one specifics
        if (levelHandler.getCurrentLevel().equals(LevelHandler.LEVEL_ONE)) {
            // handle nyan cat render level
            gameObjectsHandler.getGameObject("nyanCat").setLayer(
                gameObjectsHandler.getGameObject("nyanCat").getPosition().y
            );
        }

        // handle level transition
        if (kernelState.hasHIDEvent(HIDEventEnum.NEXT)) {
            kernelState.removeHIDEvent(HIDEventEnum.NEXT);
            String currentLevel = levelHandler.getCurrentLevel();
            String nextLevel = levelHandler.getNextLevel();
            if (nextLevel == null) {
                kernelState.setProperty(KernelStatePropertyEnum.EXIT_SIGNAL, true);
            } else {
                PRINTER.printInfo("Level transition triggered [" + currentLevel + ">>>" + nextLevel + "]...");
                kernelState.setProperty(
                    KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS,
                    gameObjectsHandler.getGameObjectManagerSet(nextLevel)
                );
                gameObjectsHandler.getGameObjectManager(GameObjectsHandler.GAME_OBJECT_MANAGER_COMMON).getLayerManager().setLayerCount(
                    kernelState.getBounds().height
                );
                gameObjectsHandler.getGameObject("nyanCat").setLayer(
                    gameObjectsHandler.getGameObject("nyanCat").getPosition().y
                );
            }
        }

        PRINTER.printTrace("Test kernel-injected pre-processor ended...");
    }

}
