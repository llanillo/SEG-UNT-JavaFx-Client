package com.seg.domain.document.dto;

import java.io.Serializable;

import com.seg.domain.enumeration.DocumentType;
import com.seg.domain.user.dto.UserBasic;

import lombok.Data;

@Data
public class DocumentEdit implements Serializable{
    
    private Long id;
    
    private String name;
    private String title;
    private String description;

    private DocumentType documentType;

    private byte[] data;

    private UserBasic coAuthor;
}
