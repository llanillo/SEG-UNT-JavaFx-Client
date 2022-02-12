package com.seg.domain.user.blueprint;

import java.util.Set;

import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.enumeration.Career;

public interface IUserResponse {
    
    Long getId();
    Long getDni();
    Long getCuil();
    void setId(Long id);
    void setDni(Long dni);
    void setCuil(Long cuil);

    String getLastname();
    String getName();
    String getEmail();
    void setLastname(String lastname);
    void setName(String name);
    void setEmail(String email);

    Set<Career> getCareer();
    Set<CommissionSummary> getCommission();
    Set<CommissionSummary> getCommissioned();
    void setCareer(Set<Career> career);
    void setCommission(Set<CommissionSummary> commission);
    void setCommissioned(Set<CommissionSummary> commissioned);

    String toString();
}
