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
import com.rpm.pixelcat.engine.test.enumeration.GameObjectHandle;
import com.rpm.pixelcat.engine.test.enumeration.GameObjectManagerHandle;
import com.rpm.pixelcat.engine.test.enumeration.LevelHandle;

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
        if (levelHandler.getCurrentLevel().equals(LevelHandle.L1)) {
            // handle nyan cat render level
            gameObjectsHandler.getGameObject(GameObjectHandle.CAT_CHARACTER).getFeature(Renderable.class).setLayer(
                gameObjectsHandler.getGameObject(GameObjectHandle.CAT_CHARACTER).getFeature(Renderable.class).getPosition().y
            );
        }

        // handle level transition
        if (kernelState.hasHIDEvent(HIDEventEnum.ENTER)) {
            kernelState.removeHIDEvent(HIDEventEnum.ENTER);
            LevelHandle currentLevel = levelHandler.getCurrentLevel();
            LevelHandle nextLevel = levelHandler.getNextLevel();
            if (nextLevel == null) {
                kernelState.addKernelAction(KernelActionEnum.EXIT);
            } else {
                PRINTER.printInfo("Level transition triggered [" + currentLevel + ">>>" + nextLevel + "]...");
                kernelState.setProperty(
                    KernelStatePropertyEnum.ACTIVE_GAME_OBJECT_MANAGERS,
                    gameObjectsHandler.getGameObjectManagerList(nextLevel)
                );
                gameObjectsHandler.getGameObjectManager(GameObjectManagerHandle.COMMON).getLayerManager().setLayerCount(
                    ((Rectangle) kernelState.getProperty(KernelStatePropertyEnum.SCREEN_BOUNDS)).height
                );
                gameObjectsHandler.getGameObject(GameObjectHandle.CAT_CHARACTER).getFeature(Renderable.class).setLayer(
                    gameObjectsHandler.getGameObject(GameObjectHandle.CAT_CHARACTER).getFeature(Renderable.class).getPosition().y
                );
            }
        }

        PRINTER.printTrace("Test kernel-injected pre-processor ended...");
    }

}
