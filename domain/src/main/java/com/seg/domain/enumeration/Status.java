package com.seg.domain.enumeration;

public enum Status {
    ACTIVE ("Activo"),        
    PENDING("Pendiente"),        
    REJECTED ("Rechazado");        
    
    private String status;    

    private Status(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {     
        return status;
    }
}
