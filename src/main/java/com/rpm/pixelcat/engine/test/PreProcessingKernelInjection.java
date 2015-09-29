package com.rpm.pixelcat.engine.test;

import com.rpm.pixelcat.engine.common.Printer;
import com.rpm.pixelcat.engine.common.PrinterFactory;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.hid.HIDEventEnum;
import com.rpm.pixelcat.engine.kernel.KernelActionEnum;
import com.rpm.pixelcat.engine.kernel.KernelInjection;
import com.rpm.pixelcat.engine.kernel.KernelState;
import com.rpm.pixelcat.engine.kernel.KernelStatePropertyEnum;
import com.rpm.pixelcat.engine.logic.gameobject.feature.Renderable;

import java.awt.*;

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
        if (kernelState.hasHIDEvent(HIDEventEnum.ESC)) {
            kernelState.addKernelAction(KernelActionEnum.EXIT);
        }

        // level one specifics
        if (levelHandler.getCurrentLevel().equals(LevelHandler.LEVEL_ONE)) {
            // handle nyan cat render level
            gameObjectsHandler.getGameObject("nyanCat").getFeature(Renderable.class).setLayer(
                gameObjectsHandler.getGameObject("nyanCat").getFeature(Renderable.class).getPosition().y
            );
        }

        // handle level transition
        if (kernelState.hasHIDEvent(HIDEventEnum.ENTER)) {
            kernelState.removeHIDEvent(HIDEventEnum.ENTER);
            String currentLevel = levelHandler.getCurrentLevel();
            String nextLevel = levelHandler.getNextLevel();
            if (nextLevel == null) {
                kernelState.addKernelAction(KernelActionEnum.EXIT);
            } else {
                PRINTER.printInfo("Level transition triggered [" + currentLevel + ">>>" + nextLevel + "]...");
                kernelState.setProperty(
                    KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS,
                    gameObjectsHandler.getGameObjectManagerSet(nextLevel)
                );
                gameObjectsHandler.getGameObjectManager(GameObjectsHandler.GAME_OBJECT_MANAGER_COMMON).getLayerManager().setLayerCount(
                    ((Rectangle) kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS)).height
                );
                gameObjectsHandler.getGameObject("nyanCat").getFeature(Renderable.class).setLayer(
                    gameObjectsHandler.getGameObject("nyanCat").getFeature(Renderable.class).getPosition().y
                );
            }
        }

        PRINTER.printTrace("Test kernel-injected pre-processor ended...");
    }

}
