package info.masterfrog.pixelcat.engine.logic.gameobject.element.aspect.animation;

import java.util.List;

public class AnimationFactory {
    private static AnimationFactory instance;

    public static AnimationFactory getInstance() {
        if (instance == null) {
            instance = new AnimationFactory();
        }

        return instance;
    }

    public AnimationSequence createAnimationSequence(List<String> cels, Long millisecondsPerCel) {
        AnimationSequence animationSequence = new AnimationSequenceImpl(cels, millisecondsPerCel);

        return animationSequence;
    }

    public AnimationSequence createAnimationSequence(Long millisecondsPerCel) {
        AnimationSequence animationSequence = new AnimationSequenceImpl(millisecondsPerCel);

        return animationSequence;
    }
}
