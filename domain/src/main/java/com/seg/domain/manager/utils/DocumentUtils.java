package com.seg.domain.manager.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import lombok.experimental.UtilityClass;

public final class DocumentUtils {
    
    private static final String FILE_NOT_FOUND = "File not found";
    private static final String IO_ERROR = "Interrupted IO process";

    public static byte[] createBytesArray (final File file){
        try{
            final InputStream in =  new FileInputStream(file);
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final byte[] buffer = new byte[8 * 1024];            

            int read;

            while ((read = in.read(buffer, 0, buffer.length)) != -1){
                out.write(buffer, 0, read);                       
            }
            
            in.close();            
            return out.toByteArray();
        }              
        catch(final FileNotFoundException e){
            System.out.println(FILE_NOT_FOUND);
            e.printStackTrace();
        }
        catch(final IOException e){
            System.out.println(IO_ERROR +  " " + file.getName());
            e.printStackTrace();
        }        
        return null;
    }    

    public static boolean replaceLine (final File file, final String oldStr, final String newStr){
        try {
            final Scanner scanner = new Scanner(file);
            final String content = "";
            String line = null;
            while (scanner.hasNextLine()){              
                line = scanner.nextLine();  
                if (!line.contains(oldStr)) content.concat(line + "\n");
                else content.concat(newStr + "\n");
            }
            file.delete();
            final FileWriter fw = new FileWriter(file);
            fw.write(content);
            
            scanner.close();
            fw.close();
            return true;
        }  
        catch(final FileNotFoundException e){
            System.out.println(FILE_NOT_FOUND);             
            e.printStackTrace();
        }        
        catch(final IOException e){
            e.printStackTrace();
            System.out.println(IO_ERROR);            
        }
                
        return false;
    }
}
