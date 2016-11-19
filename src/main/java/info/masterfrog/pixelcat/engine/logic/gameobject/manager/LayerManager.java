package info.masterfrog.pixelcat.engine.logic.gameobject.manager;

public interface LayerManager {
    void addLayer();

    void addLayers(Integer count);

    void removeLayer();

    void removeLayers(Integer count);

    void setLayerCount(Integer layerCount);

    Integer getLayerCount();

    Boolean isValidLayer(Integer layer);
}
