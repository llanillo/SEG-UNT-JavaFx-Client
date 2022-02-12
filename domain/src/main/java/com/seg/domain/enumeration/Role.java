package com.seg.domain.enumeration;

public enum Role {    
    ALL("Todas"),
    ADMINISTRATOR ("Área de Seguimiento"),
    CHEMISTRY_ANALYSIS ("Análisis Químico"), 
    CENSUS ("Censo e Impacto"),
    GEOPHYSICS ("Geofísica y Agrimensura"),
    GEOLOGY ("Geología"),
    MATERIALS ("Materiales y Equipamiento"), 
    PRESS ("Prensa y Difusión"),
    DRAFTING ("Redacción"),
    TREASURY ("Tesorería"),
    GUEST ("Invitado");
    
    private final String role;

    private Role(final String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;     
    }    
}
