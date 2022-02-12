package com.seg.domain.user.dto;

import java.io.Serializable;
import java.util.Set;

import com.seg.domain.enumeration.Career;

import lombok.Data;

@Data
public class UserEdit implements Serializable{
        
    private Long id;
    private Long dni;
    private Long cuil;
    
    private String lastname;    
    private String name;    
    private String password;     
    private String email;    
        
    private Set<Career> career;        
}
