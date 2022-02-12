package com.seg.viewcontainer.principal.manager;

import java.util.Arrays;
import java.util.List;

import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.blueprint.SceneLoader;
import com.seg.viewcontainer.resolution.Measure;
import com.seg.viewcontainer.resolution.R1024x768;
import com.seg.viewcontainer.resolution.R1366x768;
import com.seg.viewcontainer.resolution.R1680x1050;
import com.seg.viewcontainer.resolution.R1920x1080;
import com.seg.viewcontainer.resolution.R800x600;
import com.seg.viewcontainer.resolution.Resolution;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class CustomSceneLoader implements SceneLoader{        

    public static final String CSS_PATH = "/com/seg/css/";     
       
    protected Stage stage;     

    private static final List <Resolution> resolutionList;
    static {
        resolutionList = Arrays.asList(new R800x600(), 
                                        new R1024x768(), 
                                        new R1366x768(), 
                                        new R1680x1050(),
                                        new R1920x1080()); 
    }   
    
    public static final Resolution resolution;
    static{
        final double screenWidth = Screen.getPrimary().getBounds().getWidth();   
        Resolution temp = null;             
        for (final Resolution res : resolutionList){            
            if (res.getMinWidth() <= screenWidth && screenWidth <= res.getMaxWidth()) temp = res;           
        }      
        resolution = temp;    
    }
   
    public Scene prepareScene (final Path path, final Measure measure){        
        final Scene scene = loadScene(path, measure);
        scene.getStylesheets().addAll(
                        getClass().getResource(CSS_PATH + "resolution/" + resolution.getCss() + ".css").toExternalForm(),
                        getClass().getResource(CSS_PATH + "application/root.css").toExternalForm());       
        return scene;
    }

    public static final void centerStage (final Stage stage){
        final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public Stage getStage(){
        return stage;
    }

    public Resolution getResolucion(){
        return resolution;
    }
}
