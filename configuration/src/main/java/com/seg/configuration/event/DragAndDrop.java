package com.seg.configuration.event;

import java.io.File;

import org.springframework.context.ApplicationEvent;

public final class DragAndDrop extends ApplicationEvent{

    public DragAndDrop(File source) {
        super(source);        
    }
    
    public File getFile(){
        return (File) source;
    }
}
