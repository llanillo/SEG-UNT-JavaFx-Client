package com.seg.view.utils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;

public final class DocUtils {

    private static final String OS_PATH = System.getProperty("user.home");
    
    public static void fileToPdf (final File file){
        final String folderPath = file.getParent();
        final String fileName = file.getName();
        final String pdfPathDestination = folderPath + "/" + FilenameUtils.removeExtension(fileName) + ".pdf";
        final File pdfFile = new File (pdfPathDestination);        
        final File tempPath = new File(folderPath + "/" + "PDFTemporal");
        
        if (!tempPath.exists()){
            tempPath.mkdir();
        }
        
        try{            

            final IConverter converter = LocalConverter.builder()
                                            .baseFolder(tempPath)
                                            .workerPool(20, 25, 2, TimeUnit.SECONDS)
                                            .processTimeout(5, TimeUnit.SECONDS)
                                            .build();

            final Future<Boolean> conversion = converter
                                                .convert(file).as(DocumentType.MS_WORD)
                                                .to(pdfFile).as(DocumentType.PDF)
                                                .prioritizeWith(1000)
                                                .schedule();            
            converter.shutDown();            
            FileUtils.deleteDirectory(tempPath);
        }
        catch(IOException e){
            e.printStackTrace();
        }            
    }
    
    public static String sizeToString(final long size) {
        if(size <= 0) 
            return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        final int digitGroup = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroup)) + " " + units[digitGroup];
    }
        
    private String getFileExtension (final File file){
        return FilenameUtils.getExtension(file.getName());
    }
}
