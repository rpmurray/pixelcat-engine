package com.rpm.pixelcat.engine.logic.gameobject;

class LayerManagerImpl implements LayerManager {
    private Integer layerCount;

    LayerManagerImpl() {
        this(0);
    }

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
