package com.seg.domain.enumeration;

public enum DocumentType {
    IMAGE ("Imagen"),
    PDF ("PDF"),
    WORD("Word"),
    EXCEL("Excel"),
    AUTOCAD("Autocad"),
    FOLDER("Carpeta"),
    OTHER ("Otro");
    
    private final String documentType;

    private DocumentType(final String documentType) {
        this.documentType = documentType;
    }

    @Override
    public String toString() {
        return documentType;     
    }    
}
