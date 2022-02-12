package com.seg.viewcontainer.secondary.load;

import com.seg.configuration.event.StageInit;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.blueprint.SceneLoader;
import com.seg.viewcontainer.resolution.Measure;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Component
public class LoadManager implements ApplicationListener<StageInit>, SceneLoader, LoadControl{
    
    private Stage parentStage;
    private Stage stage;
                
    @Override
    public void onApplicationEvent(StageInit event) {
        stage = new Stage();
        parentStage = event.getStage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);    
        stage.initOwner(parentStage);        
    }

    @Override
    public void showInfiniteLoad(){    
        final Measure measure = new Measure (parentStage.getWidth(),  parentStage.getHeight());   
        final Scene scene = loadScene(Path.INFINITE_LOAD, measure);                

        stage.setScene(scene);    
        stage.show();        
        stage.setX(parentStage.getX());
        stage.setY(parentStage.getY());  
    } 

    @Override
    public void hideInfiniteLoad(){
        if (stage.isShowing())
            stage.close();                 
    }
}
