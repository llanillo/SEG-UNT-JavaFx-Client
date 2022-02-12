package com.seg.domain.user.fx;

import java.util.Set;

import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.enumeration.Career;
import com.seg.domain.user.blueprint.IUserResponse;

import org.springframework.util.CollectionUtils;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserResponseFx implements IUserResponse{ 
    
    @EqualsAndHashCode.Include
    private final LongProperty id = new SimpleLongProperty();
    private final LongProperty dni = new SimpleLongProperty();
    private final LongProperty cuil = new SimpleLongProperty();

    private final StringProperty lastname = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    private final SetProperty<Career> career = new SimpleSetProperty<>();
    private final SetProperty<CommissionSummary> commission = new SimpleSetProperty<>();    
    private final SetProperty<CommissionSummary> commissioned = new SimpleSetProperty<>();    
    
    @Override
    public Long getId() {
        return idProperty().get();
    }

    @Override
    public Long getDni() {
        return dniProperty().get();
    }

    @Override
    public Long getCuil() {
        return cuilProperty().get();
    }
    
    @Override
    public void setId(final Long id) {
        idProperty().set(id);
    }

    @Override
    public void setDni(final Long dni) {
        dniProperty().set(dni);
    }

    @Override
    public void setCuil(final Long cuil) {
        cuilProperty().set(cuil);
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

    @Override
    public Set<Career> getCareer() {
        return careerProperty().get();
    }

    @Override
    public Set<CommissionSummary> getCommission() {
        return commissionProperty().get();
    }
    
    @Override
    public Set<CommissionSummary> getCommissioned() {
        return commissionedProperty().get();
    }

    @Override
    public void setCareer(final Set<Career> career) {       
        if (!CollectionUtils.isEmpty(career)) 
            careerProperty().set(FXCollections.observableSet(career));                
    }

    @Override
    public void setCommission(final Set<CommissionSummary> commission) {
        if (!CollectionUtils.isEmpty(commission)) 
            commissionProperty().set(FXCollections.observableSet(commission));
    }
    
    @Override
    public void setCommissioned(final Set<CommissionSummary> commissioned) {
        if (!CollectionUtils.isEmpty(commissioned)) 
            commissionedProperty().set(FXCollections.observableSet(commissioned));
    }

    public LongProperty idProperty(){
        return id;
    }

    public LongProperty dniProperty(){
        return dni;
    }

    public LongProperty cuilProperty(){
        return cuil;
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

    public SetProperty<Career> careerProperty(){
        return career;
    }

    public SetProperty<CommissionSummary> commissionProperty(){
        return commission;
    }

    public SetProperty<CommissionSummary> commissionedProperty(){
        return commissioned;
    }

    @Override
    public String toString() {
        return getLastname() + " " + getName();
    }
}
