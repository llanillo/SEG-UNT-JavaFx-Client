package com.seg.domain.user.dto;

import java.util.Set;

import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.enumeration.Career;
import com.seg.domain.user.blueprint.IUserResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse implements IUserResponse {
    
    private Long id;        
    private Long dni;
    private Long cuil;
    
    private String lastname;
    private String name; 
    private String email;    
        
    private Set<Career> career;    
    private Set<CommissionSummary> commission;        
    private Set<CommissionSummary> commissioned;

    @Override
    public String toString() {
        return getLastname() + " " + getName();
    }   
}
