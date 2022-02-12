package com.seg.domain.document.dto;

import java.time.LocalDateTime;

import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.document.blueprint.IDocumentManage;
import com.seg.domain.enumeration.Action;
import com.seg.domain.enumeration.DocumentType;
import com.seg.domain.user.dto.UserBasic;

import lombok.Data;

@Data
public class DocumentManage implements IDocumentManage{
    
    private Long id;
    private Long size;
    
    private String name;
    private String title;    
    private String description;
        
    private Action action;
    private UserBasic author;       
    private LocalDateTime date;    
    private DocumentType documentType;  
      
    private CommissionSummary commission;       
}
