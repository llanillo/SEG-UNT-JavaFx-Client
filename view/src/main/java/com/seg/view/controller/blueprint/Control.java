package com.seg.view.controller.blueprint;

import java.net.URL;
import java.util.ResourceBundle;

import com.seg.viewcontainer.configuration.Path;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;

public abstract class Control implements Initializable{
                    
    private static IParentControl parentControl;  
    protected static Path path;                

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {      
        init();          
        initNodes();     
        initProperties();     
        initScales();            
        fieldFormat();                  
    }

    protected void fieldFormat() {}

    protected void initNodes() {}

    protected void initScales() {}

    protected void initProperties() {}

    protected void init() {}

    protected final Window eventWindow(final Event evento){
        return ((Node) evento.getSource()).getScene().getWindow();
    }

    protected final StackPane eventRoot (final ActionEvent evento){
        return (StackPane) ((Node) evento.getSource()).getScene().getRoot();
    }

    protected final void askFocus (final Node nodo){
        Platform.runLater(() -> {
            nodo.requestFocus();
        });
    }

    public final StackPane getChildContainer(){
        return parentControl.getChildSceneContainer();
    }

    public final StackPane getParentContainer(){
        return parentControl.getParentSceneContainer();
    }

    protected void setParentControl(final IParentControl parentControl){        
        Control.parentControl = parentControl;
    }
}
