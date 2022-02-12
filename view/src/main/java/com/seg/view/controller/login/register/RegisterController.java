package com.seg.view.controller.login.register;

import static com.seg.view.utils.AnimUtils.initFadeOut;
import static com.seg.view.utils.NodeUtils.createTooltip;
import static com.seg.view.utils.NodeUtils.getSelection;
import static com.seg.view.utils.TextUtils.capitalizeInitialLetters;
import static com.seg.view.utils.TextUtils.createLengthRegexPattern;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.seg.client.web.session.SessionWebClient;
import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.enumeration.Career;
import com.seg.domain.enumeration.Role;
import com.seg.domain.manager.impl.UserManager;
import com.seg.domain.user.dto.UserProperties;
import com.seg.view.concurrent.service.login.RegisterService;
import com.seg.view.controller.blueprint.EditorControl;
import com.seg.view.controller.login.container.LoginContainerControl;
import com.seg.view.controller.utils.Formato;
import com.seg.view.controller.utils.Formato.Tipo;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.secondary.load.LoadControl;

import org.springframework.stereotype.Component;

import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegisterController extends EditorControl implements RegisterControl{

    @FXML
    private MenuButton menuCommission;
    @FXML
    private MenuButton menuCareer;
    @FXML
    private Label errorLabel;
    @FXML
    private JFXButton submitBtn;
    @FXML
    private JFXTextField nameTextField;
    @FXML
    private JFXTextField lastnameTextfield;
    @FXML
    private JFXTextField emailTextField;
    @FXML
    private JFXTextField dniTextfield;
    @FXML
    private JFXTextField cuilTextfield;
    @FXML
    private JFXPasswordField passwordTextfield;
    @FXML
    private JFXPasswordField passwordRepTextfield;    

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final Duration DELAY = Duration.seconds(5);
    
    private static final BooleanProperty careerSelected = new SimpleBooleanProperty(false);
    private static final BooleanProperty commissionSelected = new SimpleBooleanProperty(false);
    
    private final SessionWebClient sessionWebClient;
    private final LoadControl loadControl;    
    private final LoginContainerControl loginContainerControl;
    
    private FadeTransition fadeOutTransition;

    @FXML
    public void backBtnClick(final ActionEvent event) {
        loginContainerControl.moveScene();
    }

    @FXML
    void submitBtnClick(final ActionEvent event) {
        if (!getTxtPassword().equals(getTxtRepPassword())) playFadeOut(MATCH_PASSWORD_MESSAGE, errorLabel, fadeOutTransition);
        else registerUser(sessionWebClient, loadControl);                    
    }

    @FXML
    public void resetBtnClick(final ActionEvent event) {
        nameTextField.clear();
        lastnameTextfield.clear();
        emailTextField.clear();
        dniTextfield.clear();
        cuilTextfield.clear();
        passwordTextfield.clear();
        passwordRepTextfield.clear();        
        menuCareer.getItems().forEach((item) -> {
            final CheckMenuItem checkItem = (CheckMenuItem) item;
            checkItem.setSelected(false);
        });
        menuCommission.getItems().forEach((item) -> {            
            final CheckMenuItem checkItem = (CheckMenuItem) item;
            checkItem.setSelected(false);
        });        
    }

    @Override
    public void failedRegister(final String mensaje) {
        playFadeOut(mensaje, errorLabel, fadeOutTransition);      
    }

    @Override
    protected void init() {
        path = Path.REGISTER;
    }

    @Override
    public void initNodes() {        
        initMenuButton(Arrays.asList(Career.values()), menuCareer, careerSelected);
        initMenuButton(Arrays.asList(Role.values()), menuCommission, commissionSelected);         
        menuCommission.getItems().remove(0);                         
        fadeOutTransition = initFadeOut(DELAY, errorLabel, 1);
        errorLabel.setOpacity(0);
    }

    @Override
    protected void initProperties() {    
        nameTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            final String nuevaPalabra = capitalizeInitialLetters(newValue);            
            nameTextField.setText(nuevaPalabra);            
        });

        lastnameTextfield.setTextFormatter(new Formato(25, Tipo.Letras));        
        lastnameTextfield.textProperty().addListener((observable, oldValue, newValue) ->{
            final String newText = capitalizeInitialLetters(newValue);            
            lastnameTextfield.setText(newText);            
        });      
        
        validateProperty(nameTextField, lastnameTextfield, emailTextField, dniTextfield, cuilTextfield);        
        validateProperty(passwordTextfield, passwordRepTextfield);
        submitBtn.disableProperty().bind(nameTextField.textProperty().length().lessThan(MIN_NAME_LONG)
                                    .or(lastnameTextfield.textProperty().length().lessThan(MIN_NAME_LONG))
                                    .or(emailTextField.textProperty().isEmpty())
                                    .or(dniTextfield.textProperty().length().isNotEqualTo(MIN_DNI_LONG))
                                    .or(cuilTextfield.textProperty().length().isNotEqualTo(MIN_CUIL_LONG))
                                    .or(passwordTextfield.textProperty().length().lessThan(MIN_PASSWORD_LONG)
                                    .and(passwordTextfield.textProperty().length().greaterThan(MAX_PASSWORD_LONG)))
                                    .or(passwordRepTextfield.textProperty().length().lessThan(MIN_PASSWORD_LONG)
                                    .and(passwordRepTextfield.textProperty().length().greaterThan(MAX_PASSWORD_LONG)))
                                    .or(careerSelected.not())
                                    .or(commissionSelected.not()));
    }
    
    @Override
    public void fieldFormat() {
        final RegexValidator rvName = createRegexValidator(MIN_NAME_MESSAGE, createLengthRegexPattern(2, 20));
        final RegexValidator rvEmail = createRegexValidator(INVALID_EMAIL_MESSAGE, EMAIL_REGEX);
        final RegexValidator rvDNI = createRegexValidator(MIN_DNI_MESSAGE, createLengthRegexPattern(MIN_DNI_LONG, MIN_DNI_LONG + 1));
        final RegexValidator rvCUIL =  createRegexValidator(MIN_CUIL_MESSAGE, createLengthRegexPattern(MIN_CUIL_LONG, MIN_CUIL_LONG + 1));
        final RegexValidator rvPassword =  createRegexValidator(MIN_PASSWORD_MESSAGE, createLengthRegexPattern(MIN_PASSWORD_LONG, MAX_PASSWORD_LONG));

        nameTextField.getValidators().add(rvName);
        lastnameTextfield.getValidators().add(rvName);
        emailTextField.getValidators().add(rvEmail);
        dniTextfield.getValidators().add(rvDNI);
        cuilTextfield.getValidators().add(rvCUIL);
        passwordTextfield.getValidators().add(rvPassword);
        passwordRepTextfield.getValidators().add(rvPassword);
        
        nameTextField.setTextFormatter(new Formato(25, Tipo.Letras));
        lastnameTextfield.setTextFormatter(new Formato(25, Tipo.Letras));
        dniTextfield.setTextFormatter(new Formato(9, Tipo.Numeros));
        cuilTextfield.setTextFormatter(new Formato(12, Tipo.Numeros));
        passwordTextfield.setTextFormatter(new Formato(15, Tipo.Clave));
        passwordRepTextfield.setTextFormatter(new Formato(15, Tipo.Clave));
    }

    private <T extends Object> void initMenuButton(final List <T> menuList, final MenuButton menuButton, final BooleanProperty menuProperty){
        for (final T obj : menuList) {
            final CheckMenuItem item = new CheckMenuItem(obj.toString());

            item.selectedProperty().addListener((o, oldVal, newVal) -> {
                int selected = 0;
                for (final MenuItem m : menuButton.getItems()) {
                    final CheckMenuItem cm = (CheckMenuItem) m;
                    if (cm.isSelected()){
                        selected++;
                        break;
                    }
                }
                if (selected > 0) menuProperty.set(true);
                else menuProperty.set(false);
            });

            menuButton.getItems().add(item);
        }
    }

    private void registerUser(final SessionWebClient sessionWebClient, final LoadControl loadControl){
        final UserManager userManager = new UserManager();
        final Set<Career> careers = getSelection(Career.class, menuCareer);
        final Set<Role> roles = getSelection(Role.class, menuCommission);
        final Set<CommissionSummary> commissions = roles.stream().map((e) -> new CommissionSummary(null, e)).collect(Collectors.toSet());
        final UserProperties userProperties = userManager.create(getLongDNI(), getLongCuil(), getTxtLastname(), getTxtName(), getTxtPassword(), getTxtEmail(), careers, commissions);
        final RegisterService registerService = new RegisterService(sessionWebClient, this, loadControl, userProperties);
        
        registerService.start();        
    }

    private RegexValidator createRegexValidator(final String text, final String pattern){
        final RegexValidator regexValidator = new RegexValidator(text);
        regexValidator.setRegexPattern(pattern);
        regexValidator.setErrorTooltipSupplier(() -> createTooltip(Pos.TOP_CENTER));
        return regexValidator;
    }

    private Long getLongCuil(){
        return Long.parseLong(getTxtCuil());
    }
    
    private Long getLongDNI(){
        return Long.parseLong(getTxtDNI());
    }
    
    private String getTxtName(){
        return nameTextField.getText();
    }
    
    private String getTxtLastname(){
        return lastnameTextfield.getText();
    }
    
    private String getTxtEmail(){
        return emailTextField.getText();
    }
    
    private String getTxtDNI(){
        return dniTextfield.getText();
    }
    
    private String getTxtCuil(){
        return cuilTextfield.getText();
    }
    
    private String getTxtPassword(){
        return passwordTextfield.getText();
    }
    
    private String getTxtRepPassword(){
        return passwordRepTextfield.getText();
    }
}
