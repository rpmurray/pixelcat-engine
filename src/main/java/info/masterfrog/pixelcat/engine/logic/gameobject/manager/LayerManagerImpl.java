package info.masterfrog.pixelcat.engine.logic.gameobject.manager;

class LayerManagerImpl implements LayerManager {
    private Integer layerCount;

    LayerManagerImpl(Integer layerCount) {
        this.layerCount = layerCount;
    }

    public void addLayer() {
        layerCount++;
    }

    public void addLayers(Integer count) {
        layerCount += count;
    }

    public void removeLayer() {
        layerCount--;
    }

    public void removeLayers(Integer count) {
        layerCount -= count;
    }

    public void setLayerCount(Integer layerCount) {
        this.layerCount = layerCount;
    }

    public Integer getLayerCount() {
        return layerCount;
    }

    public Boolean isValidLayer(Integer layer) {
        return layer >= 0 && layer < layerCount;
    }
}
