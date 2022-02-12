package com.seg.view.concurrent.service.login;

import com.seg.client.web.blueprint.BaseWebClient;
import com.seg.client.web.session.SessionWebClient;
import com.seg.domain.jwt.JwtResponse;
import com.seg.domain.jwt.LoginRequest;
import com.seg.domain.jwt.TokenResponse;
import com.seg.view.concurrent.service.blueprint.IService;
import com.seg.view.controller.login.session.LoginControl;
import com.seg.view.session.SessionManager;
import com.seg.viewcontainer.configuration.Path;
import com.seg.viewcontainer.principal.blueprint.SceneControl;
import com.seg.viewcontainer.secondary.load.LoadControl;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginService extends Service<Void> implements IService{

    private final SessionWebClient sessionWebClient;
    private final SceneControl sceneControl;
    private final LoadControl loadControl;
    private final LoginControl loginControl;
    private final LoginRequest loginRequest;

    @Override
    protected Task<Void> createTask() {
        final Task <Void> task = new Task <>() {
            @Override
            protected Void call() throws Exception {                 
                    final JwtResponse jwtResponse = sessionWebClient.login(loginRequest.getDni(), 
                                                                String.valueOf(loginRequest.getPassword()));
                                                                loginRequest.deletePassword();                    
                    final TokenResponse tokenResponse = new TokenResponse(jwtResponse.getAccessToken(), jwtResponse.getRefreshToken(), jwtResponse.getType());
                    SessionManager.create(jwtResponse.getUser());
                    BaseWebClient.setToken(tokenResponse);              
                return null;                                     
            }
        };
        
        task.setOnRunning(e ->{
            loadControl.showInfiniteLoad();            
        });
        
        task.setOnSucceeded(e -> {
            sceneControl.changeParentScene(Path.PRINCIPAL);
            loadControl.hideInfiniteLoad(); 
        });
        
        task.setOnFailed(e-> {
            loadControl.hideInfiniteLoad();
            loginControl.loginFailed(task.getException().getMessage());
        });
        
        setExecutor(createExecutor());
        return task;
    }
}
