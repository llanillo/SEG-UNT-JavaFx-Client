package com.seg.view.controller.secondary.commission;

import static com.seg.view.utils.NodeUtils.scaleNode;

import java.io.File;

import com.jfoenix.transitions.JFXFillTransition;
import com.seg.configuration.Config;
import com.seg.configuration.event.DragAndDrop;
import com.seg.view.controller.blueprint.Control;
import com.seg.view.utils.Dimension;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DragAndDropController extends Control{

    @FXML
    private VBox vBox;
    @FXML
    private SVGPath uploadSvg;

    private static final Duration DURATION = Duration.seconds(0.3f);
    private static final Color NO_HOVER_COLOR = Color.rgb(196, 221, 252);
    private static final Color HOVER_COLOR = Color.WHITE; 
    private static final double UPLOAD_SVG_SCALE = 250;    
    private static final float SPACE_BETWEEN = 0.1f;        

    private boolean success = false;    
 
    @FXML
    void vBoxClick(final MouseEvent event) {
        final FileChooser fileChooser = new FileChooser();
        final File file = fileChooser.showOpenDialog(eventWindow(event));
        if (file != null && file.length() > 0){
            Config.getContext().publishEvent(new DragAndDrop(file));
        }
    }

    @Override
    protected void initProperties() {                       
        propiedadDrag(vBox);        
        vBox.spacingProperty().bind(vBox.heightProperty().multiply(SPACE_BETWEEN));
    }

    @Override
    protected void initScales() {
        final Dimension dimension = Dimension.WIDTH;
        scaleNode(UPLOAD_SVG_SCALE, dimension, vBox, uploadSvg);
    }

    private void propiedadDrag (final VBox vBox){
        vBox.setOnDragEntered((event) -> {
            if (event.getGestureSource() != vBox && event.getDragboard().getFiles().size() == 1)
                playBackgroundAnimation(vBox, DURATION, NO_HOVER_COLOR, HOVER_COLOR);
        });

        vBox.setOnDragExited((event) -> {
            playBackgroundAnimation(vBox, DURATION, HOVER_COLOR, NO_HOVER_COLOR);
        });

        vBox.setOnDragOver(event -> {
            if (event.getGestureSource() != vBox && event.getDragboard().getFiles().size() == 1)
                event.acceptTransferModes(TransferMode.COPY);
            event.consume();
        });

        vBox.setOnDragDropped(event -> {            
            final Dragboard db = event.getDragboard();            
            success = false;
            if (db.hasFiles() && db.getFiles().size() == 1){                
                final File file = db.getFiles().get(0);
                Config.getContext().publishEvent(new DragAndDrop(file));
                success = true;
            }
            event.setDropCompleted(success);
        });
    }

    private void playBackgroundAnimation (final VBox vBox, final Duration duration, final Color from, final Color to){        
        final JFXFillTransition fill = new JFXFillTransition(duration, vBox);                      
        fill.setFromValue(from);          
        fill.setToValue(to);                   
        fill.play();
    }
}
