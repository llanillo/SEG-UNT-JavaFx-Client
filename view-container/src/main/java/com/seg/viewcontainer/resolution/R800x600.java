package com.seg.viewcontainer.resolution;

public final class R800x600 extends Resolution {
    
    public R800x600() {        
        loginView = new Measure (580, 400);
        principalView = new Measure (700, 460);
        downloadBarView = new Measure (165, 27);                        
        
        css = "800x600";
        
        minWidth = 600;
        maxWidth = 800;
    }
}
