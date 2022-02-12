package com.seg.domain.user.dto;

import java.io.Serializable;

import com.seg.domain.user.blueprint.IUserBasic;

import lombok.Data;

@Data
public class UserBasic implements IUserBasic, Serializable{
    
    Long id;   

    String lastname;
    String name;        
    String email;    
}
