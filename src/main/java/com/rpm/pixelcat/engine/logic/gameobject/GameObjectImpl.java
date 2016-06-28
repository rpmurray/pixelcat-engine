package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.TransientGameException;
import com.rpm.pixelcat.engine.logic.common.IdGeneratorImpl;
import com.rpm.pixelcat.engine.logic.gameobject.feature.Feature;
import com.rpm.pixelcat.engine.logic.gameobject.feature.FeatureImpl;

import java.util.HashMap;
import java.util.Map;

class GameObjectImpl extends IdGeneratorImpl implements GameObject {
    private Map<Class<? extends Feature>, Feature> features;
    private Map<Class<? extends Feature>, Boolean> featuresStatus;
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
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "Feature interface does not exist", feature);
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

    public <F extends Feature> F getFeature(Class<F> featureClass) throws TransientGameException {
        // validate
        if (!hasFeature(featureClass)) {
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "Feature not avalable: " + featureClass.getSimpleName());
        }
        if (!isFeatureActive(featureClass)) {
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR, "Feature not active: " + featureClass.getSimpleName());
        }

        // fetch feature
        F feature = (F) features.get(featureClass);

        return feature;
    }

    public <F extends Feature> void deactivateFeature(Class<F> featureClass) throws TransientGameException {
        // validate
        if (!hasFeature(featureClass)) {
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR);
        }

        // deactivate
        featuresStatus.put(featureClass, false);
    }

    public <F extends Feature> void activateFeature(Class<F> featureClass) throws TransientGameException {
        // validate
        if (!hasFeature(featureClass)) {
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR);
        }

        // activate
        featuresStatus.put(featureClass, true);
    }

    public <F extends Feature> Boolean isFeatureActive(Class<F> featureClass) throws TransientGameException {
        // validate
        if (!hasFeature(featureClass)) {
            throw new TransientGameException(GameErrorCode.LOGIC_ERROR);
        }

        // fetch status
        Boolean status = featuresStatus.get(featureClass);

        return status;
    }

    public <F extends Feature> Boolean isFeatureAvailable(Class<F> featureClass) {
        // setup
        Boolean status;

        try {
            // generate status
            status = hasFeature(featureClass) && isFeatureActive(featureClass);
        } catch (TransientGameException e) {
            return false;
        }

        return status;
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
