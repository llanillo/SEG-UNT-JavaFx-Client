package com.seg.view.concurrent.service.login;

import com.seg.client.web.session.SessionWebClient;
import com.seg.domain.user.dto.UserProperties;
import com.seg.view.concurrent.service.blueprint.IService;
import com.seg.view.controller.login.register.RegisterControl;
import com.seg.viewcontainer.secondary.load.LoadControl;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterService extends Service<Void> implements IService{    

    private final SessionWebClient sessionWebClient;
    private final RegisterControl registerControl;
    private final LoadControl loadControl;
    private final UserProperties userProperties;   
     
   
    @Override
    protected Task<Void> createTask() {        
        final Task <Void> task = new Task<>() {

            @Override
            protected Void call() throws Exception {
                sessionWebClient.register(userProperties);
                return null;                                    
            }
        };
        
        task.setOnRunning(e ->{
            loadControl.showInfiniteLoad();
        });
        
        task.setOnSucceeded(e -> {            
            loadControl.hideInfiniteLoad();
            registerControl.resetBtnClick(null);
            registerControl.backBtnClick(null);
        });
        
        task.setOnFailed(e-> {            
            loadControl.hideInfiniteLoad();
            registerControl.failedRegister(task.getException().getMessage());
        });
        
        setExecutor(createExecutor());
        return task;        
    }
}
