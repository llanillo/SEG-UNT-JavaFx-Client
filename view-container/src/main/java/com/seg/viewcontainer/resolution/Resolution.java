package com.seg.viewcontainer.resolution;

import lombok.Getter;

@Getter
public abstract class Resolution {
        
    protected Measure loginView;
    protected Measure principalView;
    protected Measure downloadBarView;

    protected String css;
    protected double minWidth;
    protected double maxWidth;
}
