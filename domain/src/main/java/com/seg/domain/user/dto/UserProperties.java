package com.seg.domain.user.dto;

import java.io.Serializable;
import java.util.Set;

import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.enumeration.Career;

import lombok.Value;

@Value
public class UserProperties implements Serializable{
                   
    private Long dni;        
    private Long cuil;
        
    private String lastname;    
    private String name;        
    private String password;         
    private String email;    
                    
    private Set<Career> career;        
    private Set<CommissionSummary> commission;    
}