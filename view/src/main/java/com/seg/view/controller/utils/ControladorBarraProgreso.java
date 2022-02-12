package com.seg.view.controller.utils;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOutDown;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

 @Controller
public class ControladorBarraProgreso implements Initializable{

    @FXML
    private Label txtProgreso;
    @FXML
    private ProgressBar barraProgreso;
    @FXML
    private GridPane panePadre;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        mostrar();    
    }

    public void mostrar(){
        new FadeInUp (panePadre).setCycleCount(1).play();      
    }
    
    public void ocultar(){        
        new FadeOutDown (panePadre).setCycleCount(1).setDelay(Duration.seconds(1)).play();
    }    
    
    public void asignarTarea (Task<Void> tarea){
        barraProgreso.progressProperty().bind(tarea.progressProperty());   
    }
}
