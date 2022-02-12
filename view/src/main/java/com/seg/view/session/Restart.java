package com.seg.view.session;

import java.util.List;

import com.seg.view.controller.blueprint.Logout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Restart {
    
    @Autowired
    private List <Logout> logOutBeans;

    public void restartBeans (){
        for (Logout bean : logOutBeans){
            bean.restartFields();
        }
    }

}
