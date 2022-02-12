package com.seg.viewcontainer.principal.manager;

import com.seg.configuration.event.StageInit;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.blueprint.SceneControl;
import com.seg.viewcontainer.resolution.Measure;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

@Component
public final class SceneManager extends CustomSceneLoader implements ApplicationListener<StageInit>, SceneControl{
    
    @Override
    public void onApplicationEvent(final StageInit event) {        
        stage = event.getStage();        
        changeParentScene(Path.LOGIN_CONTAINER);      
    }

    @Override
    public void changeParentScene (final Path path){     
        final Measure measure;
        switch(path){
            case LOGIN_CONTAINER:
                measure = resolution.getLoginView();
                break;
            default:
                measure = resolution.getPrincipalView();
                break;
        }
        final Scene scene = prepareScene(path, measure);                
        stage.setScene(scene);         
        centerStage(stage);
    }
     
    public void changeChildScene(final Path path, final StackPane stackPane){
        final Parent childScene = loadFxml(path);        
        stackPane.getChildren().clear();
        stackPane.getChildren().add(childScene);        
    }         
}
