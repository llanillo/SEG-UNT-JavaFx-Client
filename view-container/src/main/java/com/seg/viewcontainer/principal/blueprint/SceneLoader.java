package com.seg.viewcontainer.principal.blueprint;

import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.resolution.Measure;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public interface SceneLoader extends FxmlLoader{
    
    default Scene loadScene (final Path path, final Measure measure){        
        final Parent root = loadFxml(path);                        
        final Scene scene = new Scene (root, measure.getWidth(), measure.getHeight());                  
        scene.setFill(Color.TRANSPARENT);
        return scene;
    }
}
