package com.seg.view.controller.utils;

import javafx.scene.control.TextFormatter;

public final class Formato extends TextFormatter<Integer>{
    
    public enum Tipo {
        Letras ("^[a-zA-Z\\s]*$"),
        Numeros ("^[\\d]*$"),
        Clave ("^[^\\s]*$");
        
        private final String pauta;

        private Tipo(String pauta) {
            this.pauta = pauta;
        }
    }
    
    public Formato(int limCaracteres, Tipo tipo) {
        super((Change change) -> {
            if (change.getControlNewText().length() <= limCaracteres){
                if (change.getText().matches(tipo.pauta)){
                    return change;
                }   
            }
            return null;
        });
    }
    
    public Formato(Tipo tipo) {
        super((Change change) -> {
            if (change.getText().matches(tipo.pauta)){
                return change;
            } 
            return null;
        });
    } 
}
