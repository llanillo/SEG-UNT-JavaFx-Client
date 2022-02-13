package com.seg.view.utils;


import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public final class AnimUtils {
    
    public static FadeTransition initFadeOut(final Duration duration, final Node node, final int cycleCount){
        final FadeTransition fadeOut = new FadeTransition();
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setNode(node);
        fadeOut.setDelay(duration);
        fadeOut.setCycleCount(cycleCount);
        return fadeOut;
    }
}
