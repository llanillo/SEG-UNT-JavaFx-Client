package com.seg.domain.user.blueprint;

public interface IUserBasic {
    
    Long getId();
    void setId(Long id);

    String getLastname();
    String getName();
    String getEmail();
    void setLastname(String lastname);
    void setName(String name);
    void setEmail(String email);
}
