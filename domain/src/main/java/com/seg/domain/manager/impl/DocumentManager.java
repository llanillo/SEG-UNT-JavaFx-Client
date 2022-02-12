package com.seg.domain.manager.impl;

import static com.seg.domain.manager.utils.DocumentUtils.createBytesArray;

import java.io.File;
import java.time.LocalDateTime;

import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.document.dto.DocumentProperties;
import com.seg.domain.enumeration.DocumentType;
import com.seg.domain.user.dto.UserBasic;

public final class DocumentManager {
 
    public DocumentProperties create(final UserBasic author, final String title, final String description, final LocalDateTime date, final DocumentType documentType, final CommissionSummary commission, final File file){
        final DocumentProperties documentProperties = new DocumentProperties();          
        documentProperties.setSize(file.length());
        documentProperties.setName(file.getName());
        documentProperties.setTitle(title);
        documentProperties.setDate(date);
        documentProperties.setDescription(description);
        documentProperties.setCommission(commission);
        documentProperties.setAuthor(author);
        documentProperties.setDocumentType(documentType);
        documentProperties.setData(createBytesArray(file));   
        return documentProperties;
    }          
}
