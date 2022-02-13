package com.seg.view.controller.blueprint;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;

public abstract class EditorControl extends Control{

    protected static final int MIN_NAME_LONG = 3;
    protected static final int MIN_DNI_LONG = 2;
    protected static final int MIN_CUIL_LONG = 2;
    protected static final int MIN_PASSWORD_LONG = 2;
    protected static final int MAX_PASSWORD_LONG = 20;
        
    protected static final String MIN_NAME_MESSAGE = "Debe tener al menos " + MIN_NAME_LONG + " caracteres";
    protected static final String MIN_PASSWORD_MESSAGE = "La contraseña debe tener entre " + MIN_PASSWORD_LONG + " y " + MAX_PASSWORD_LONG + " caracteres";
    protected static final String MIN_CUIL_MESSAGE = "El Cuil debe tener entre " + MIN_CUIL_LONG + " y " + (MIN_CUIL_LONG + 1) + " caracteres";
    protected static final String MIN_DNI_MESSAGE = "El DNI debe tener entre " + MIN_DNI_LONG + " y " + (MIN_DNI_LONG + 1) + " caracteres";
    protected static final String INVALID_EMAIL_MESSAGE = "Por favor, introduce un correo electrónico valido";
    protected static final String MATCH_PASSWORD_MESSAGE = "Las contraseñas deben ser iguales";    

    protected static final String GENERAL_ERROR = "Ha surgido un error";
    protected static final String ATTENTION_TITLE = "¡Atención!";

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        super.initialize(url, rb);        
    }

    protected boolean emptyFields(){return false;}

    protected final void validateProperty(final JFXTextField ... textFields){
        for (JFXTextField textField : textFields) {
            textField.focusedProperty().addListener((o, oldVal, newVal) -> {
                if(!newVal) textField.validate();            
            });
        }
    }

    protected final void validateProperty(final JFXPasswordField ... textFields){
        for (JFXPasswordField textField : textFields) {
            textField.focusedProperty().addListener((o, oldVal, newVal) -> {
                if(!newVal) textField.validate();            
            });
        }
    }

    protected final void validateProperty(final JFXComboBox<?> ... comboBoxes){
        for (JFXComboBox<?> comboBox : comboBoxes) {
            comboBox.focusedProperty().addListener((o, oldVal, newVal) -> {
                if (!newVal) comboBox.validate();
            });
        }
    }

    protected void playFadeOut (final String message, final Label label, final FadeTransition fadeTransition){
        label.setOpacity(1);
        label.setText(message);
        fadeTransition.play();
    }
}
