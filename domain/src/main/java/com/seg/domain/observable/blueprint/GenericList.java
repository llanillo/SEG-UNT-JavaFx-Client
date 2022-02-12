package com.seg.domain.observable.blueprint;

import java.util.List;
import java.util.function.Predicate;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public abstract class GenericList <T extends Object>{
        
    private final ObservableList <T> observableList = FXCollections.observableArrayList(); 
    private final FilteredList<T> filteredList = new FilteredList<>(observableList);
    
    private final IntegerBinding countBinding = Bindings.size(filteredList);
    
    public final void update(final List <T> list){
        observableList.clear();
        observableList.addAll(FXCollections.observableArrayList(list));
    }
    
    public final void addAll(final List<T> list){
        observableList.addAll(list);
    }

    public final void add (final T entidad){
        observableList.add(entidad);
    }
         
    public final void remove (final T entidad){
        observableList.remove(entidad);
    }
    
    public FilteredList <T> getList(){
        return filteredList;
    }

    public ObjectProperty<Predicate <? super T>> filterProperty(){
        return filteredList.predicateProperty();
    }

    public ReadOnlyObjectProperty<ObservableList<T>> listProperty() { 
        return new SimpleObjectProperty<>(observableList); 
    }

    public IntegerBinding getCountBinding() {
        return countBinding;
    }

}
