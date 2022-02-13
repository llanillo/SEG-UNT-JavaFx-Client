package com.seg.view.controller.login.session;

import static com.seg.view.utils.AnimUtils.initFadeOut;
import static com.seg.view.utils.NodeUtils.createTooltip;
import static com.seg.view.utils.NodeUtils.scaleNode;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.seg.client.web.session.SessionWebClient;
import com.seg.domain.jwt.LoginRequest;
import com.seg.view.concurrent.service.login.LoginService;
import com.seg.view.controller.blueprint.EditorControl;
import com.seg.view.controller.login.container.LoginContainerControl;
import com.seg.view.controller.utils.Formato;
import com.seg.view.controller.utils.Formato.Tipo;
import com.seg.view.utils.Dimension;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.blueprint.SceneControl;
import com.seg.viewcontainer.secondary.load.LoadControl;

import org.springframework.stereotype.Component;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginController extends EditorControl implements LoginControl{

    @FXML
    private GridPane gridPane;
    @FXML
    private Button closeBtn;
    @FXML
    private SVGPath closeSvg;
    @FXML
    private Label errorLabel;
    @FXML
    private SVGPath dniSvg;
    @FXML
    private JFXTextField dniTextfield;
    @FXML
    private SVGPath passSvg;
    @FXML
    private JFXPasswordField passwordTextfield;
    @FXML
    private JFXButton submitBtn;
    @FXML
    private JFXCheckBox dniCheckBox;

    private static final Duration DELAY = Duration.seconds(5);
    private static final String REQUIRED_FIELD = "Este campo es obligatorio";

    private static final double ICON_SCALE = 30;
        
    private final SessionWebClient sessionWebClient;
    private final LoginContainerControl loginContainerControl;
    private final SceneControl sceneControl;
    private final LoadControl loadControl;

    private FadeTransition fadeOutTransition;

    @FXML
    void submitBtnClick(final ActionEvent event) {
        final LoginService loginService = new LoginService(sessionWebClient, sceneControl, loadControl, this, 
                                                            new LoginRequest(getTxtDNI(), getTxtPassword().toCharArray()));
        loginService.start();

        // final UserResponse usuarioRespuestaFX = new UserResponse();
        // usuarioRespuestaFX.setLastname("apellido");
        // usuarioRespuestaFX.setName("nombre");
        // SessionManager.create(usuarioRespuestaFX);
        
        // sceneControl.changeParentScene(Path.PRINCIPAL);
    }

    @FXML
    void passwordTextAction(final ActionEvent event) {
        submitBtnClick(event);
    }
    
    @FXML
    void registerBtnClick(final ActionEvent event) {
        loginContainerControl.moveScene();
    }

    @FXML
    void closeBtnClick(final ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void loginFailed(final String mensaje) {
        playFadeOut(mensaje, errorLabel, fadeOutTransition);
    }

    @Override
    protected void init() {
        path = Path.LOGIN;
    }

    @Override
    public void initNodes() {             
        super.askFocus(submitBtn);      
        fadeOutTransition = initFadeOut(DELAY, errorLabel, 1);     
        errorLabel.setOpacity(0);
    }

    @Override    
    protected void initProperties() {        
        validateProperty(dniTextfield);
        validateProperty(passwordTextfield);
        submitBtn.disableProperty().bind(dniTextfield.textProperty().isEmpty().or(
                                        passwordTextfield.textProperty().isEmpty()));
    }

    @Override
    public void initScales() {     
        final Dimension dimension = Dimension.HEIGHT;           
        scaleNode(ICON_SCALE, dimension, dniTextfield, dniSvg, passSvg);        
        scaleNode(ICON_SCALE, dimension, closeBtn, closeSvg);        
    }

    @Override
    public void fieldFormat() {                     
        final RequiredFieldValidator rfv = new RequiredFieldValidator(REQUIRED_FIELD);                      
        rfv.setErrorTooltipSupplier(() -> createTooltip(Pos.TOP_CENTER));
        dniTextfield.getValidators().addAll(rfv);
        passwordTextfield.getValidators().add(rfv);        

        dniTextfield.setTextFormatter(new Formato(9, Tipo.Numeros));       
        passwordTextfield.setTextFormatter(new Formato(12, Tipo.Clave));                  
    }

    private String getTxtDNI(){
        return dniTextfield.getText();
    }
    
    private String getTxtPassword(){
        return passwordTextfield.getText();
    }
}
