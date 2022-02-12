package com.seg.domain.user.fx;

import com.seg.domain.user.blueprint.IUserBasic;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserBasicFx implements IUserBasic{
    
    @EqualsAndHashCode.Include
    private final LongProperty id = new SimpleLongProperty();
    
    private final StringProperty lastname = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    @Override
    public Long getId(){
        return idProperty().get();
    }

    @Override
    public String getLastname() {
        return lastnameProperty().get();
    }

    @Override
    public String getName() {
        return nameProperty().get();
    }

    @Override
    public String getEmail() {
        return emailProperty().get();
    }

    @Override
    public void setId(final Long id){
        idProperty().set(id);
    }

    @Override
    public void setLastname(final String lastname) {
        lastnameProperty().set(lastname);
    }

    @Override
    public void setName(final String name) {
        nameProperty().set(name);        
    }

    @Override
    public void setEmail(final String email) {
        emailProperty().set(email);        
    }
    
    public LongProperty idProperty(){
        return id;
    }

    public StringProperty lastnameProperty(){
        return lastname;
    }

    public StringProperty nameProperty(){
        return name;
    }

    public StringProperty emailProperty(){
        return email;
    }

    @Override
    public String toString() {
        return getLastname() + " " + getName();
    }    
}
