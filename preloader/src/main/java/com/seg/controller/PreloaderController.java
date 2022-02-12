package com.seg.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXProgressBar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;

public class PreloaderController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private Label label;
    @FXML
    private SVGPath svgLogo;
    @FXML
    private JFXProgressBar jfxProgressBar;

    private static final double LOGO_SCALE = 700;
    private static final String INITIAL_LABEL = "Iniciando. . .";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scaleSvg(LOGO_SCALE, gridPane, svgLogo);
        label.setText(INITIAL_LABEL);
    }

    public void scaleSvg(final double scale, final Region region, final SVGPath svg){        
        svg.scaleXProperty().bind(region.widthProperty().divide(scale));
        svg.scaleYProperty().bind(region.widthProperty().divide(scale));
    }    
}