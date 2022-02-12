package com.seg.view.utils;


import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AnimUtils {
    
    public FadeTransition initFadeOut(final Duration duration, final Node node, final int cicle){
        final FadeTransition fadeOut = new FadeTransition();
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setNode(node);
        fadeOut.setDelay(duration);
        fadeOut.setCycleCount(cicle);
        return fadeOut;
    }
}
