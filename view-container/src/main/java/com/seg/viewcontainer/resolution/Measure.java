package com.seg.viewcontainer.resolution;

import lombok.Getter;

@Getter
public final class Measure {
    
    final private double width;
    final private double height;

    public Measure(final double width, final double height) {
        this.width = width;
        this.height = height;
    }   
}
