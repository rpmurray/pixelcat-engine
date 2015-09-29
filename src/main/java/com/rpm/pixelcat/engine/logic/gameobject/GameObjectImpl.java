package com.rpm.pixelcat.engine.logic.gameobject;

import com.rpm.pixelcat.engine.exception.GameErrorCode;
import com.rpm.pixelcat.engine.exception.GameException;
import com.rpm.pixelcat.engine.logic.common.IdGeneratorImpl;
import com.rpm.pixelcat.engine.logic.gameobject.feature.Feature;

import java.util.HashMap;
import java.util.Map;

class GameObjectImpl extends IdGeneratorImpl implements GameObject {
    private Map<Class<? extends Feature>, Feature> features;
    private Map<Class<? extends Feature>, Boolean> featuresStatus;
    private GameObjectProperties properties;

    GameObjectImpl(GameObjectProperties properties) throws GameException {
        // handle super
        super(GameObject.class.toString());

        // handle init
        init(properties);
    }

    private void init(GameObjectProperties properties) throws GameException {
        // features
        this.features = new HashMap<>();
        this.featuresStatus = new HashMap<>();

        // properties
        this.properties = properties;
    }

    public GameObject registerFeature(Feature feature) {
        return registerFeature(feature, true);
    }

    public GameObject registerFeature(Feature feature, Boolean status) {
        // register feature
        features.put(feature.getClass(), feature);

        // register feature status
        featuresStatus.put(feature.getClass(), status);

        return this;
    }

    public <F extends Feature> Boolean hasFeature(Class<F> featureClass) {
        return features.containsKey(featureClass);
    }

    public <F extends Feature> F getFeature(Class<F> featureClass) throws GameException {
        // validate
        if (!hasFeature(featureClass) || !isFeatureActive(featureClass)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        // fetch feature
        F feature = (F) features.get(featureClass);

        return feature;
    }

    public <F extends Feature> void deactivateFeature(Class<F> featureClass) throws GameException {
        // validate
        if (!hasFeature(featureClass)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        // deactivate
        featuresStatus.put(featureClass, false);
    }

    public <F extends Feature> void activateFeature(Class<F> featureClass) throws GameException {
        // validate
        if (!hasFeature(featureClass)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        // activate
        featuresStatus.put(featureClass, true);
    }

    public <F extends Feature> Boolean isFeatureActive(Class<F> featureClass) throws GameException {
        // validate
        if (!hasFeature(featureClass)) {
            throw new GameException(GameErrorCode.LOGIC_ERROR);
        }

        // fetch status
        Boolean status = featuresStatus.get(featureClass);

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
