package com.seg.view.controller.blueprint;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPopup;

import javafx.scene.control.ListCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.VBox;

public abstract class TableControl extends Control{
    
    protected static final double DOWNLOAD_BTN_SCALE = 45;

    protected static final String DOWNLOAD_TEXT = "Descargar";

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {        
        super.initialize(arg0, arg1);
        initTableElements();   
        initColumns();
        initRows();
        filterSearchProperty();        
    }
   
    protected abstract void initColumns();    
  
    protected abstract void initRows ();
         
    protected abstract void initTableElements ();  
     
    protected abstract void filterSearchProperty();

    protected VBox initContextMenu(){
        throw new RuntimeException("initContextMenu must be overridden");
    }

    protected <T extends Object> void initSortComboBox (final TreeTableView<T> table, 
    final JFXComboBox<TreeTableColumn<T, ?>> comboBox, final List<TreeTableColumn<T, ?>> columns){
        
        final TreeTableColumn.SortType ascendingOrder = TreeTableColumn.SortType.ASCENDING;
        final TreeTableColumn.SortType descendingOrder = TreeTableColumn.SortType.DESCENDING;
        
        comboBox.valueProperty().addListener((o, oldVal, newVal) -> {            
            if(newVal != null){
                if (newVal == oldVal){
                    final TreeTableColumn.SortType result = (newVal.getSortType() == ascendingOrder) ? descendingOrder : ascendingOrder;
                    newVal.setSortType(result);
                }
                else{
                    table.getSortOrder().setAll(Collections.singleton(newVal));
                    newVal.setSortType(ascendingOrder);
                }                
            }
            else table.getSortOrder().clear();
        });

        comboBox.getItems().addAll(columns);        
        comboBox.setCellFactory((list) -> initComboBoxCellFactory());
        comboBox.setButtonCell(initComboBoxCellFactory());  
    }

    private <T extends Object> ListCell<TreeTableColumn<T, ?>> initComboBoxCellFactory(){
        return new ListCell <TreeTableColumn<T, ?>>(){
            @Override
            protected void updateItem(TreeTableColumn<T, ?> item, boolean empty) {                
                super.updateItem(item, empty);

                if (item == null || empty){
                    setGraphic(null);
                    setText(null);
                }
                else{
                    setText(item.getText());
                }
            }            
        };
    }

    protected final JFXPopup createPopup(){
        final JFXPopup jfxPopup = new JFXPopup();
        jfxPopup.setPopupContent(initContextMenu());
        return jfxPopup;
    }
}
