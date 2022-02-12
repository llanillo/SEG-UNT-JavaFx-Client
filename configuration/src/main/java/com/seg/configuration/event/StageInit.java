package com.seg.configuration.event;

import org.springframework.context.ApplicationEvent;

import javafx.stage.Stage;

public final class StageInit extends ApplicationEvent{
    
    public StageInit(final Stage source){
        super(source);
    }

    public Stage getStage(){
        return (Stage) getSource();
    }
}