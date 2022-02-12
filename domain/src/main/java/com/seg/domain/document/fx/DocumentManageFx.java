package com.seg.domain.document.fx;

import java.time.LocalDateTime;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.seg.domain.commission.dto.CommissionSummary;
import com.seg.domain.document.blueprint.IDocumentManage;
import com.seg.domain.enumeration.Action;
import com.seg.domain.enumeration.DocumentType;
import com.seg.domain.enumeration.Status;
import com.seg.domain.user.dto.UserBasic;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DocumentManageFx extends RecursiveTreeObject<DocumentManageFx> implements IDocumentManage{
    
    @EqualsAndHashCode.Include
    private final LongProperty id = new SimpleLongProperty();
    private final LongProperty size = new SimpleLongProperty();
    
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();

    private final ObjectProperty<Action> action = new SimpleObjectProperty<>();
    private final ObjectProperty<Status> status = new SimpleObjectProperty<>();
    private final ObjectProperty<UserBasic> author = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> date = new SimpleObjectProperty<>();
    private final ObjectProperty<DocumentType> documentType = new SimpleObjectProperty<>();

    private final ObjectProperty<CommissionSummary> commission = new SimpleObjectProperty<>();

    @Override
    public Long getId() {
        return idProperty().get();
    }

    @Override
    public Long getSize() {
        return sizeProperty().get();
    }

    @Override
    public void setId(final Long id) {
        idProperty().set(id);
    }

    @Override
    public void setSize(final Long size) {
        sizeProperty().set(size);        
    }

    @Override
    public String getName() {
        return nameProperty().get();
    }

    @Override
    public String getTitle() {
        return titleProperty().get();
    }

    @Override
    public String getDescription() {
        return descriptionProperty().get();
    }

    @Override
    public void setName(final String name){
        nameProperty().set(name);
    }

    @Override
    public void setTitle(final String title) {
        titleProperty().set(title);       
    }

    @Override
    public void setDescription(final String description) {
        descriptionProperty().set(description);        
    }

    public Action getAction() {
        return actionProperty().get();
    }

    public Status getStatus() {
        return statusProperty().get();
    }

    @Override
    public UserBasic getAuthor() {
        return authorProperty().get();
    }

    @Override
    public LocalDateTime getDate() {
        return dateProperty().get();
    }

    @Override
    public DocumentType getDocumentType() {
        return documentTypeProperty().get();
    }

    public void setAction(final Action action){
        actionProperty().set(action);
    }

    public void setStatus(final Status status){
        statusProperty().set(status);
    }

    @Override
    public void setAuthor(final UserBasic author) {
        authorProperty().set(author);        
    }

    @Override
    public void setDate(final LocalDateTime date) {
        dateProperty().set(date);        
    }

    @Override
    public void setDocumentType(final DocumentType documentType) {
        documentTypeProperty().set(documentType);
    }

    public CommissionSummary getCommission() {
        return commissionProperty().get();
    }

    public void setCommission(final CommissionSummary commissionFunction){
        commissionProperty().set(commissionFunction);
    }

    public LongProperty idProperty(){
        return id;
    }

    public LongProperty sizeProperty(){
        return size;
    }

    public StringProperty nameProperty(){
        return name;
    }

    public StringProperty titleProperty(){
        return title;
    }

    public StringProperty descriptionProperty(){
        return description;
    }

    public ObjectProperty<Action> actionProperty(){
        return action;
    }

    public ObjectProperty<Status> statusProperty(){
        return status;
    }

    public ObjectProperty<UserBasic> authorProperty(){
        return author;
    }

    public ObjectProperty<LocalDateTime> dateProperty(){
        return date;
    }
    
    public ObjectProperty<DocumentType> documentTypeProperty(){
        return documentType;
    }

    public ObjectProperty<CommissionSummary> commissionProperty(){
        return commission;
    }
}
