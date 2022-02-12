package com.seg.view.controller.secondary.general;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

@Component
public class LoadController implements Initializable {

    @FXML
    private Circle leftCircle;
    @FXML
    private Circle middleCircle;
    @FXML
    private Circle rightCircle;
    
    private static final Duration leftDelay = Duration.millis(500);
    private static final Duration middleDelay = Duration.millis(750);
    private static final Duration rightDelay = Duration.millis(1000);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new FadeIn (leftCircle).setCycleCount(100).setDelay(leftDelay).play();
        new FadeIn (middleCircle).setCycleCount(100).setDelay(middleDelay).play();
        new FadeIn (rightCircle).setCycleCount(100).setDelay(rightDelay).play();
    }
}
