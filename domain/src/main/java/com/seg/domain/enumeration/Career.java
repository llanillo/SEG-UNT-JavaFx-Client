package com.seg.domain.enumeration;

public enum Career {
    INGENIERIA_AGRIMENSURA ("Ingeniería Agrimensura"),
    INGENIERIA_CIVIL ("Ingeniería Cívil"),
    INGENIERIA_COMPUTACION ("Ingeniería en Computación"),
    INGENIERIA_GEOFISICA_GEODESICA ("Ingeniería Geodésica y Geofísica"),            
    INGENIERIA_INDUSTRIAL ("Ingeniería Industrial"),    
    INGENIERIA_QUIMICA ("Ingeniería Química"),      
    GEOGRAFIA ("Profesorado y Licenciatura en Geografía"),
    INGLES ("Licenciatura en Inglés"),
    GEOLOGIA ("Geología");
    
    private final String career;

    private Career(final String career) {
        this.career = career;
    }

    @Override
    public String toString() {
        return career;
    }
}
