package com.seg.view.controller.principal;

import static com.seg.view.utils.NodeUtils.scaleNode;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.seg.view.controller.blueprint.TableControl;
import com.seg.view.session.SessionManager;
import com.seg.view.utils.Dimension;

import org.springframework.stereotype.Component;

import animatefx.animation.FadeInUp;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WelcomeController extends TableControl{

    @FXML
    private Circle circleLeft;
    @FXML
    private Circle circleMiddle; 
    @FXML
    private Circle circleRight;
    @FXML
    private Label userLabel;
    @FXML
    private GridPane circlesGridPane;
    @FXML
    private JFXListView<?> listView;
    
    private static final String IMAGE_PATH = "/com/seg/image/extra/";
    private static final double CIRCLE_SCALE = 130;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {                
        super.initialize(arg0, arg1);               
        iniciarCirculos(); 
        initAnimation(circleLeft, circleMiddle, circleRight, listView, userLabel);
    }

    @Override
    protected void initNodes() {
        final SessionManager sesion = SessionManager.instance();
        userLabel.setText(sesion.getUser().toString());
    }

    @Override
    public void initScales() {
        
    }

    @Override
    public void initColumns(){

    }

    @Override
    public void initRows() {
        
    }

    @Override
    public void initTableElements() {
        
    }

    @Override
    public void filterSearchProperty() {
        
    }  
    
    private void iniciarCirculos(){
        final Image leftImage = new Image(IMAGE_PATH + "img1.jpg") {};
        final Image middleImage = new Image (IMAGE_PATH + "img2.jpg");
        final Image rightImage = new Image (IMAGE_PATH + "img3.jpg");

        scaleNode(CIRCLE_SCALE, Dimension.HEIGHT, circlesGridPane, circleLeft, circleMiddle, circleRight);
        
        circleLeft.setFill(new ImagePattern(leftImage));
        circleMiddle.setFill(new ImagePattern(middleImage));
        circleRight.setFill(new ImagePattern(rightImage));               
    }

    private void initAnimation (final Node... nodes){
        for (Node n: nodes){
            new FadeInUp(n).setCycleCount(1).setSpeed(0.8).play();
        }
    }    
}
