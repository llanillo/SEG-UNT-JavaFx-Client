package com.seg.domain.document.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.enumeration.DocumentType;
import com.seg.domain.user.dto.UserBasic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentProperties implements Serializable{
        
    private Long size;
    
    private String name;
    private String title;    
    private String description;
        
    private CommissionSummary commission;                
    private UserBasic author;       
    private LocalDateTime date;    
    private DocumentType documentType;    
    
    private byte[] data;

    private Set<UserBasic> coAuthor;    
}
