package com.seg.domain.commission.dto;

import com.seg.domain.enumeration.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommissionSummary {
    
    private Long id;
    private Role role;

    @Override
    public String toString() {
        return role.toString();
    }
}
