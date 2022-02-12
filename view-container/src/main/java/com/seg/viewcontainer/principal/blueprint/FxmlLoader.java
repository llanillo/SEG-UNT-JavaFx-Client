package com.seg.viewcontainer.principal.blueprint;

import com.seg.viewcontainer.configuration.FxmlProcessor;
import com.seg.viewcontainer.configuration.Path;

import javafx.scene.Parent;

public interface FxmlLoader {
   
    String LOAD_ERROR = "Could not load this path";

    default Parent loadFxml (final Path path){
        final FxmlProcessor fxmlProcessor = new FxmlProcessor();
        Parent root = null;
        
        try{            
            root = fxmlProcessor.loadFxml(path);
        }
        catch (Exception e){
            System.out.println(LOAD_ERROR + " " + path.toString());
            e.printStackTrace();
        }
        return root;
    }   
}
