package com.seg.view.controller.blueprint;

import java.net.URL;
import java.util.ResourceBundle;

import com.seg.viewcontainer.principal.manager.SceneManager;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ParentControl extends Control implements IParentControl{        
    
    private double xOffset = 0;
    private double yOffset = 0;      
    
    protected final SceneManager sceneManager;

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {        
        setParentControl(this);
        super.initialize(url, rb);        
    }

    protected void movableNode(final Node node){
        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                sceneManager.getStage().setX(event.getScreenX() - xOffset);
                sceneManager.getStage().setY(event.getScreenY() - yOffset);
            }
        });
    }    
}
