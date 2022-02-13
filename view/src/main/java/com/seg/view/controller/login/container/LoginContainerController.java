package com.seg.view.controller.login.container;

import com.seg.view.controller.blueprint.ParentControl;
import com.seg.view.utils.Dimension;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.manager.SceneManager;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static com.seg.view.utils.NodeUtils.scaleNode;

@Component
public class LoginContainerController extends ParentControl implements LoginContainerControl{

    @FXML
    private StackPane stackPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private StackPane stackPaneChild;    
    @FXML
    private SVGPath logoSvg;
        
    private final Duration TRANSITION_DURATION = Duration.seconds(1.5);     
    private final Duration FADE_DURATION = Duration.seconds(0.75);       
    private final double LOGO_SCALE = 550;    

    private Parent register;
    private Parent login;    

    private int factor = -1;
    
    public LoginContainerController(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {        
        super.initialize(url, rb);
        super.movableNode(stackPane);        
    }
    
    @Override
    public void initNodes() {
        login = sceneManager.loadFxml(Path.LOGIN);
        register = sceneManager.loadFxml(Path.REGISTER);
        register.setVisible(false);
        stackPaneChild.getChildren().addAll(login, register);     
    }

    @Override
    protected void initScales() {        
        scaleNode(LOGO_SCALE, Dimension.WIDTH, gridPane, logoSvg);
    }

    @Override
    public StackPane getChildSceneContainer() {
        return stackPaneChild;
    }

    @Override
    public StackPane getParentSceneContainer() {        
        return stackPane;
    } 

    @Override
    public final void moveScene(){
        final TranslateTransition translateMainContent = new TranslateTransition(TRANSITION_DURATION, stackPaneChild);
        final TranslateTransition translateLogo = new TranslateTransition(TRANSITION_DURATION, gridPane);
        final FadeTransition fadeOut = new FadeTransition(FADE_DURATION, stackPaneChild);
        final FadeTransition fadeIn = new FadeTransition(FADE_DURATION, stackPaneChild);
        final ParallelTransition parallelTransition = new ParallelTransition(translateMainContent, translateLogo, fadeOut);
        
        final double xPosMainContent = factor * (stackPane.getWidth() - stackPaneChild.getWidth());
        final double xPosLogo = factor * (-1) * (stackPane.getWidth() - gridPane.getWidth()); 
        
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);        
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        translateMainContent.setByX(xPosMainContent);
        translateLogo.setByX(xPosLogo);

        fadeOut.setOnFinished(actionEvent -> {
            fadeIn.play();

            if (factor == 1){
                login.setVisible(false);
                register.setVisible(true);
            }
            else{
                login.setVisible(true);
                register.setVisible(false);
            }
        });

        parallelTransition.play();               
        factor *= -1;
    }
}
