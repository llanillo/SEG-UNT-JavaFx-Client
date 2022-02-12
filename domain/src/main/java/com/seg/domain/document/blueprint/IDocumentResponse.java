package com.seg.domain.document.blueprint;

import java.time.LocalDateTime;

import com.seg.domain.enumeration.DocumentType;
import com.seg.domain.user.dto.UserBasic;

public interface IDocumentResponse {
    
    Long getId();
    Long getSize();
    void setId(Long id);
    void setSize(Long size);

    String getName();
    String getTitle();
    String getDescription();
    void setName(String name);
    void setTitle(String title);
    void setDescription(String description);

    UserBasic getAuthor();
    LocalDateTime getDate();
    DocumentType getDocumentType();
    void setAuthor(UserBasic author);
    void setDate(LocalDateTime date);
    void setDocumentType(DocumentType documentType);
}
