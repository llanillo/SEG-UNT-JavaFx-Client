package com.seg.domain.enumeration;

import java.util.EnumSet;

public interface Search{

    public static <T extends Enum<T>> T findEnumValue(final Class<T> enumClass, final String value) throws IllegalArgumentException{
        for(T obj : EnumSet.allOf(enumClass)){
            if(obj.toString().equalsIgnoreCase(value)){
                return obj;
            }
        }
        throw new IllegalArgumentException("Enum search failed: " + enumClass.getCanonicalName());
    }
}
