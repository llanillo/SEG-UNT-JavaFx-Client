package com.seg.view.controller.principal.commission;

import static com.seg.view.utils.NodeUtils.scaleNode;

import java.io.File;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.seg.view.controller.blueprint.Control;
import com.seg.view.session.SessionManager;
import com.seg.view.utils.Dimension;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.blueprint.SceneControl;

import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.DirectoryChooser;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SeeDocumentController extends Control{

    @FXML
    private GridPane gridPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label commissionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private JFXTextField authorTextfield;
    @FXML
    private Pane authorsPane;
    @FXML
    private JFXTextField documentTypeTextfield;
    @FXML
    private JFXTextField titleTextfield;
    @FXML
    private JFXTextArea descriptionTextarea;
    @FXML
    private JFXButton downloadBtn;
    @FXML
    private SVGPath downloadSvg;
    @FXML
    private Label documentSizeLabel;
    @FXML
    private StackPane stackPane;

    private static final double SVG_SCALE = 50;

    private final SceneControl sceneControl;

    private SessionManager sessionManager;

    @FXML
    void bckBtnClick(ActionEvent event) {
        final Path r = (path == Path.COMMISSION_TABLE) ? Path.COMMISSION_TABLE : Path.MY_DOCUMENTS;        
        sceneControl.changeChildScene(r, getChildContainer());  
    }

    @FXML
    void downloadBtnClick(ActionEvent event) {
        final DirectoryChooser selectorCarpeta = new DirectoryChooser();
        final File carpetaElegida = selectorCarpeta.showDialog(eventWindow(event));
        
        // if (Objects.nonNull(carpetaElegida) && Objects.nonNull(archivoSeleccionado)){
            // tareaArchivo.descargarArchivo(archivoSeleccionado, carpetaElegida, false);
        // }
    }
        
    @Override
    public void initNodes(){ 
        // authorTextfield.setText(archivoSeleccionado.getAutor().toString());
        // dateLabel.setText(archivoSeleccionado.fechaFormato());
        // titleLabel.setText(archivoSeleccionado.getTitulo());
        // descriptionTextarea.setText(archivoSeleccionado.getDescripcion());
        // documentTypeTextfield.setText(archivoSeleccionado.getTipo().toString());
        // documentSizeLabel.setText(tamañoEnString(archivoSeleccionado.getTamaño()));     
    } 

    @Override
    public void initScales(){            
        scaleNode(SVG_SCALE, Dimension.HEIGHT, downloadBtn, downloadSvg);
    }   
}
