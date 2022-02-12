package com.seg.domain.manager.impl;

import java.util.Set;

import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.enumeration.Career;
import com.seg.domain.enumeration.Role;
import com.seg.domain.user.blueprint.IUserResponse;
import com.seg.domain.user.dto.UserEdit;
import com.seg.domain.user.dto.UserProperties;

import org.springframework.util.CollectionUtils;

public final class UserManager{    

    public UserProperties create(final Long dni, final Long cuil, final String lastname, final String name, final String clave, final String email, final Set<Career> career, final Set<CommissionSummary> commission){        
        return new UserProperties(dni, cuil, lastname, name, clave, email, career, commission);       
    }
     
    public void assignValues (final UserEdit user, final String name, final String lastname, final String email, final Long dni, final Long cuil, final Set <CommissionSummary> commission){        
        user.setName (name);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setDni(dni);
        user.setCuil(cuil);
        // usuario.setComision(comisiones);                 
    }    
   
    public void assignValues (final UserEdit user, final String name, final String lastname, final String email, final String password){   
        if(password.compareTo("0") != 0){            
            // final BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
            // final String claveCodificada = codificador.encode(clave);
            // usuario.setClave(claveCodificada);
        }
        
        user.setName (name);
        user.setLastname(lastname);
        user.setEmail(email);               
    }   
    
    public boolean isMemberOfCommission (final IUserResponse user, final Role role){
        if (!CollectionUtils.isEmpty(user.getCommission())){
            for(CommissionSummary commission : user.getCommission()){
                if(commission.getRole() == role){
                    return true;
                }
            }
        }
        return false;
    }
}
