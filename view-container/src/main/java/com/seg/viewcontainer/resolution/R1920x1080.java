package com.seg.viewcontainer.resolution;

public class R1920x1080 extends Resolution{

    public R1920x1080() {        
        loginView = new Measure (880, 605);
        principalView = new Measure (1200, 750);
        downloadBarView = new Measure (220, 40);        

        css = "1920x1080";
        
        minWidth = 1681;
        maxWidth = 1920;
    }    
}
