package com.seg.view.utils;

import static com.seg.view.utils.Dimension.HEIGHT;
import static com.seg.view.utils.Dimension.WIDTH;

import java.util.HashSet;
import java.util.Set;

import com.jfoenix.controls.JFXTooltip;
import com.seg.domain.enumeration.Search;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NodeUtils {

    private final static Duration HOVER_DURATION = Duration.seconds(0.2);
        
    public void scaleNode (final double scale, final Node ... nodes){
        for (Node n: nodes){
            n.setScaleX(scale);
            n.setScaleY(scale);            
        }
    }

    public void scaleNode(final double scale, final Dimension dimension, final Region region, final SVGPath... svg){
        for (SVGPath s: svg){
            if (dimension == HEIGHT){
                s.scaleXProperty().bind(region.heightProperty().divide(scale));
                s.scaleYProperty().bind(region.heightProperty().divide(scale));            
            }            
            else if (dimension == WIDTH){
                s.scaleXProperty().bind(region.widthProperty().divide(scale));
                s.scaleYProperty().bind(region.widthProperty().divide(scale));
            }
        }
    }

    public void scaleNode(final double scale, final Dimension dimension, final Region region, final Shape... shapes){
        for (Shape s : shapes) {
            if (dimension == HEIGHT){
                s.scaleXProperty().bind(region.heightProperty().divide(scale));
                s.scaleYProperty().bind(region.heightProperty().divide(scale));
            }
            else if (dimension == WIDTH){
                s.scaleXProperty().bind(region.widthProperty().divide(scale));
                s.scaleYProperty().bind(region.widthProperty().divide(scale));
            }
        }
    }
    
    public SVGPath createSvg (final String content, final Color color){
        final SVGPath svg = new SVGPath ();
        svg.setContent(content);
        svg.setFill(color);        
        return svg;
    }    
         
    public void createTooltip (final Node node, final String text, final Pos position){
        final JFXTooltip tooltip = new JFXTooltip();        
        tooltip.setHoverDelay(HOVER_DURATION);
        tooltip.setPos(position);
        tooltip.setText(text);
        JFXTooltip.install(node, tooltip);
    }    
          
    public JFXTooltip createTooltip (final String text, final Pos position){
        final JFXTooltip tooltip = new JFXTooltip();        
        tooltip.setHoverDelay(HOVER_DURATION);
        tooltip.setPos(position);
        tooltip.setText(text);
        return tooltip;
    }      
 
    public JFXTooltip createTooltip (final Pos position){
        final JFXTooltip tooltip = new JFXTooltip();        
        tooltip.setHoverDelay(HOVER_DURATION);        
        tooltip.setPos(position);
        return tooltip;
    }            

    public boolean isSelected(final MenuButton menuButton){
        for (MenuItem item : menuButton.getItems()){
            final CheckMenuItem checkItem = (CheckMenuItem) item;
            if (checkItem.isSelected())
                return true;
        }
        return false;
    }    
        
    public <T extends Enum<T>> Set<T> getSelection(final Class<T> enumClass, final MenuButton menuButton){        
        final Set<T> set = new HashSet<>();       
        
        for (MenuItem item : menuButton.getItems()){
            final CheckMenuItem checkItem = (CheckMenuItem) item;            
            if (checkItem.isSelected()){
                T obj = Search.findEnumValue(enumClass, checkItem.getText());
                set.add(obj);
            }
        }    
        return set;
    }        
}
