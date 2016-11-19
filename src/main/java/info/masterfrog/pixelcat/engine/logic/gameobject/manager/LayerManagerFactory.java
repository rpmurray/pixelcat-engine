package info.masterfrog.pixelcat.engine.logic.gameobject.manager;

interface LayerManagerFactory {
    static LayerManager create() {
        return LayerManagerFactory.create(1);
    }

    static LayerManager create(Integer layerCount) {
        LayerManager layerManager = new LayerManagerImpl(layerCount);

        return layerManager;
    }
}
