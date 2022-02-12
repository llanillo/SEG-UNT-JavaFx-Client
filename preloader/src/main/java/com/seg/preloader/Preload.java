package com.seg.preloader;
import java.io.IOException;

import com.seg.configuration.Config;
import com.seg.configuration.event.StageInit;
import com.seg.viewcontainer.principal.manager.CustomSceneLoader;
import com.seg.viewcontainer.principal.manager.SceneManager;

import javafx.animation.FadeTransition;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Preload extends Preloader{
    
    private static final String FXML_PATH = "/com/seg/fxml/Preloader.fxml";
    private static final String ERROR_FXML = "Could not load the preloader FXML";
    
    private Stage stage;

    private Parent createSplashScreen() {
        try{                     
            return FXMLLoader.load(getClass().getResource(FXML_PATH));    
        }
        catch(final IOException e){
            System.out.println(ERROR_FXML);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void start(final Stage stage) throws Exception {
        this.stage = stage;                        
        final Scene scene = new Scene(createSplashScreen());
        scene.getStylesheets().add(getClass().getResource(CustomSceneLoader.CSS_PATH + "resolution/" + CustomSceneLoader.resolution.getCss() + ".css").toExternalForm());
        stage.setTitle("SEG UNT");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);     
    }

    @Override
    public void handleProgressNotification(final ProgressNotification pn) {        
        if (pn.getProgress() != -1 && !stage.isShowing()){
            stage.show();
            SceneManager.centerStage(stage);
        }
    }

    @Override
    public void handleStateChangeNotification(final StateChangeNotification evt) {        
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START){
            if (stage.isShowing()){
                final FadeTransition ft = new FadeTransition(Duration.seconds(2), stage.getScene().getRoot());                                
                ft.setFromValue(1.0);
                ft.setToValue(0.7);                                                
                ft.setOnFinished(new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(final ActionEvent arg0) {                        
                        Config.getContext().publishEvent(new StageInit(stage));
                    }

                });

                ft.play();
            }
        }
    } 
}
