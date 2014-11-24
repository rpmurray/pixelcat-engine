package com.rpm.pixelcat.logic.gameobject;

public class LayerManager {
    private Integer layerCount;

    private static LayerManager instance;

    private LayerManager() {
        layerCount = 0;
    }

    public static LayerManager getInstance() {
        if (instance == null) {
            instance = new LayerManager();
        }

        return instance;
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

    public Integer getLayerCount() {
        return layerCount;
    }
}
