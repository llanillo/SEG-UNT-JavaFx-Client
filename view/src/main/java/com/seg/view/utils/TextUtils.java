package com.seg.view.utils;

import java.util.ArrayList;
import java.util.List;

public final class TextUtils {
         
    public static String capitalizeInitialLetters (final String text){
        if (text.length() <= 0)
            return "";
        else{
            final char lastChar = text.charAt(text.length() - 1);
                
            if (Character.isWhitespace(lastChar))
                return text;             
            else{
                final String [] words = text.trim().split(" ");
                final List <String> capitalizedWords = new ArrayList<String>();

                for (String word : words){            
                    final char [] wordChar = word.toCharArray();

                    for (int i = 0; i < wordChar.length; i++){
                        if (i == 0)                    
                            wordChar[i] = Character.toUpperCase(word.charAt(i));                    
                        else
                            wordChar[i] = Character.toLowerCase(word.charAt(i));                
                    }  
                    String aux = String.valueOf(wordChar);
                    capitalizedWords.add(aux);
                }            
                return String.join(" ", capitalizedWords);            
            }            
        }
    }

    public static String createLengthRegexPattern(final int min, final int max){
        return "^[0-9a-zA-Z]{" + min + "," + max +"}$";
    }
}
