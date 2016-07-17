package info.masterfrog.pixelcat.engine.logic.gameobject;

public interface LayerManager {
    public void addLayer();

    public void addLayers(Integer count);

    public void removeLayer();

    public void removeLayers(Integer count);

    public void setLayerCount(Integer layerCount);

    public Integer getLayerCount();

    public Boolean isValidLayer(Integer layer);
}