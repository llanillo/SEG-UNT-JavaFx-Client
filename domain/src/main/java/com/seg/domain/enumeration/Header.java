package com.seg.domain.enumeration;

public enum Header {
    BEARER ("Bearer "),
    AUTHORIZATION ("Authorization");

    private final String header;

    private Header(final String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return header;
    }        
}
