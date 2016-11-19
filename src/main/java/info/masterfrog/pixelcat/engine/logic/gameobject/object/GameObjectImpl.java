package info.masterfrog.pixelcat.engine.logic.gameobject.object;

import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;
import info.masterfrog.pixelcat.engine.kernel.CanvasManager;
import info.masterfrog.pixelcat.engine.kernel.KernelState;
import info.masterfrog.pixelcat.engine.kernel.KernelStateProperty;
import info.masterfrog.pixelcat.engine.logic.common.IdGeneratorImpl;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.canvas.Canvas;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.property.GameObjectProperties;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.Feature;
import info.masterfrog.pixelcat.engine.logic.gameobject.element.feature.FeatureImpl;

import java.util.HashMap;
import java.util.Map;

class GameObjectImpl extends IdGeneratorImpl implements GameObject {
    private Map<Class<? extends Feature>, Feature> features;
    private Map<Class<? extends Feature>, Boolean> featuresStatus;
    private Canvas canvas;
    private GameObjectProperties properties;

    GameObjectImpl(GameObjectProperties properties) throws TransientGameException {
        // handle super
        super(GameObject.class.getSimpleName());

        // handle init
        init(properties);
    }

    private void init(GameObjectProperties properties) throws TransientGameException {
        // features
        this.features = new HashMap<>();
        this.featuresStatus = new HashMap<>();
        this.canvas = KernelState.getInstance().<CanvasManager>getProperty(KernelStateProperty.CANVAS_MANAGER).getDefault();

        // properties
        this.properties = properties;
    }

    public <F extends Feature> GameObject registerFeature(F feature) throws TransientGameException {
        return registerFeature(feature, true);
    }

    public <F extends Feature> GameObject registerFeature(F feature, Boolean status) throws TransientGameException {
        // setup
        Class<? extends Feature> featureIntf = null;

        // derive appropriate interface
        for (Class intf : feature.getClass().getInterfaces()) {
            if (feature.getClass().getSimpleName().equals(intf.getSimpleName() + "Impl") &&
                !feature.getClass().equals(FeatureImpl.class) &&
                intf.isInterface()) {
                featureIntf = intf;
            }
        }

        // if we didn't find a matching interface, throw an error
        if (featureIntf == null) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR, "Feature interface does not exist", feature);
        }

        // register feature
        features.put(featureIntf, feature);

        // register feature status
        featuresStatus.put(featureIntf, status);

        return this;
    }

    public <F extends Feature> Boolean hasFeature(Class<F> featureClass) {
        return features.containsKey(featureClass);
    }

    public <F extends Feature> void verifyHasFeature(Class<F> featureClass) throws TransientGameException {
        if (!features.containsKey(featureClass)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR, featureClass.getSimpleName() + " feature is not available", this);
        }
    }

    public <F extends Feature> F getFeature(Class<F> featureClass) throws TransientGameException {
        // validate
        verifyFeatureIsAvailable(featureClass);

        // fetch feature
        F feature = (F) features.get(featureClass);

        return feature;
    }

    public <F extends Feature> void deactivateFeature(Class<F> featureClass) throws TransientGameException {
        // validate
        if (!hasFeature(featureClass)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        // deactivate
        featuresStatus.put(featureClass, false);
    }

    public <F extends Feature> void activateFeature(Class<F> featureClass) throws TransientGameException {
        // validate
        if (!hasFeature(featureClass)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR);
        }

        // activate
        featuresStatus.put(featureClass, true);
    }

    public <F extends Feature> Boolean isFeatureActive(Class<F> featureClass) {
        // check existence
        if (!hasFeature(featureClass)) {
            return false;
        }

        // fetch status
        Boolean status = featuresStatus.get(featureClass);

        return status;
    }

    public <F extends Feature> void verifyFeatureIsActive(Class<F> featureClass) throws TransientGameException {
        // check existence
        if (!hasFeature(featureClass)) {
            return;
        }

        // validate
        if (!featuresStatus.get(featureClass)) {
            throw new TransientGameException(GameEngineErrorCode.LOGIC_ERROR, featureClass.getName() + " feature is not active", this);
        }
    }

    public <F extends Feature> Boolean isFeatureAvailable(Class<F> featureClass) {
        // generate status
        Boolean status = hasFeature(featureClass) && isFeatureActive(featureClass);

        return status;
    }

    public <F extends Feature> void verifyFeatureIsAvailable(Class<F> featureClass) throws TransientGameException {
        verifyHasFeature(featureClass);
        verifyFeatureIsActive(featureClass);
    }

    public GameObject setCanvas(Canvas canvas) {
        this.canvas = canvas;

        return this;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GameObjectProperties getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "GameObjectImpl{" +
            "features=" + features +
            ", featuresStatus=" + featuresStatus +
            ", properties=" + properties +
            '}';
    }
}
