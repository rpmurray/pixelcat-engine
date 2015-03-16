package com.rpm.pixelcat.logic.animation;

import com.rpm.pixelcat.logic.resource.Resource;

import java.util.List;

public class AnimationFactory {
    private static AnimationFactory instance;

    public static AnimationFactory getInstance() {
        if (instance == null) {
            instance = new AnimationFactory();
        }

        return instance;
    }

    public AnimationSequence createAnimationSequence(List<Resource> cels, Long millisecondsPerCel) {
        AnimationSequence animationSequence = new AnimationSequenceImpl(cels, millisecondsPerCel);

        return animationSequence;
    }

    public AnimationSequence createAnimationSequence(Long millisecondsPerCel) {
        AnimationSequence animationSequence = new AnimationSequenceImpl(millisecondsPerCel);

        return animationSequence;
    }
}
