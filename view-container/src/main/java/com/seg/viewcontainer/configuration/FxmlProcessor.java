package com.seg.viewcontainer.configuration;

import java.io.IOException;

import com.seg.configuration.Config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public final class FxmlProcessor {
    
    public Parent loadFxml (final Path path)throws IOException{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource(path.toString()));        
        loader.setControllerFactory(Config.getContext()::getBean);        
        return loader.load();
    }
}
