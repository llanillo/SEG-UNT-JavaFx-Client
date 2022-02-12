package com.seg.viewcontainer.resolution;

public final class R1680x1050 extends Resolution {

    public R1680x1050() {        
        loginView = new Measure (800, 530);
        principalView = new Measure (1050, 650);
        downloadBarView = new Measure (220, 40);        

        css = "1680x1050";
        
        minWidth = 1367;
        maxWidth = 1680;
    }        
}
