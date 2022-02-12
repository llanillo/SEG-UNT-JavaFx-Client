package com.seg.viewcontainer.resolution;

public final class R1366x768 extends Resolution {

    public R1366x768() {
        loginView = new Measure (750, 500);
        principalView = new Measure (920, 600);
        downloadBarView = new Measure (200, 35);                            

        css = "1366x768";
        
        minWidth = 1025;
        maxWidth = 1366;
    }
}
