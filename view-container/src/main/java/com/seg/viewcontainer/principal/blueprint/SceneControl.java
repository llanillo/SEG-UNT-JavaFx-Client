package com.seg.viewcontainer.principal.blueprint;

import com.seg.viewcontainer.configuration.Path;

import javafx.scene.layout.StackPane;

public interface SceneControl {
    
    void changeParentScene (final Path path);

    void changeChildScene(final Path path, final StackPane stackPane);
}
