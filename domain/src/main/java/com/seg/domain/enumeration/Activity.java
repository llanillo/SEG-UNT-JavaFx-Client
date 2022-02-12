package com.seg.domain.enumeration;

public enum Activity {
    REGISTER ("Se registró", ""),
    ADD ("Añadió","Se registró"),
    EDIT ("Editó", "Editó sus datos"),
    DELETE ("Borró", "Eliminó a"),
    APPROVE_ADD ("Aprobó", "Aceptó a"),
    APPROVE_EDIT ("Aprobó la edición", ""),
    APPROVE_DELETE ("Aprobó eliminar", ""),
    MODIFY ("Solicitó modificar", "Editó los datos de"),    
    REJECTED_ADD ("Rechazó", "Rechazó a"),
    REJECTED_EDIT ("Rechazo la edición", ""),
    REJECTED_DELETE ("Rechazó eliminar", ""),
    MANAGER ("Asignó Encargado","Asignó como Encargado"),
    DELETE_MANAGER ("Removió de Encargado", "Removió como Encargado");
    
    private final String verb;
    private final String action;

    private Activity(final String verb, final String action) {
        this.verb = verb;
        this.action = action;
    }

    public String getVerb() {
        return verb;
    }

    public String getAction() {
        return action;
    }
}
