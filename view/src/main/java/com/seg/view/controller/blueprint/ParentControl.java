package com.seg.view.controller.blueprint;

import com.seg.viewcontainer.principal.manager.SceneManager;
import javafx.scene.Node;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

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
        node.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        node.setOnMouseDragged(event -> {
            sceneManager.getStage().setX(event.getScreenX() - xOffset);
            sceneManager.getStage().setY(event.getScreenY() - yOffset);
        });
    }    
}
