package com.rpm.pixelcat.engine.logic.gameobject;

public interface LayerManager {
    public void addLayer();

    public void addLayers(Integer count);

    public void removeLayer();

    public void removeLayers(Integer count);

    public Integer getLayerCount();

    public Boolean isValidLayer(Integer layer);
}
