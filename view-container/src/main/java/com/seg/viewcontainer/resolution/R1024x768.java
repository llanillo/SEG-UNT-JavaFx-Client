package com.seg.viewcontainer.resolution;

public final class R1024x768 extends Resolution {

    public R1024x768() {
        loginView = new Measure (650, 480);
        principalView = new Measure (820, 530);
        downloadBarView = new Measure (180, 30);                        

        css = "1024x768";
        
        minWidth = 801;
        maxWidth = 1024;
    }
}
