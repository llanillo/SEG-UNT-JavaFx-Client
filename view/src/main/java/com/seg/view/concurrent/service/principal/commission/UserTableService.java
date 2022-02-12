package com.seg.view.concurrent.service.principal.commission;

import java.util.List;

import com.seg.client.web.user.UserWebClient;
import com.seg.domain.observable.list.UserBasicList;
import com.seg.domain.user.dto.UserBasic;
import com.seg.domain.user.fx.UserBasicFx;
import com.seg.view.concurrent.service.blueprint.UserService;
import com.seg.view.controller.principal.container.NotificationControl;

import org.modelmapper.ModelMapper;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserTableService extends Service<Void> implements UserService{

    private final UserWebClient userWebClient;
    private final NotificationControl notificationControl;
    private final UserBasicList userBasicList;
    private final ModelMapper modelMapper;
    
    @Override
    protected Task<Void> createTask() {
        final Task<Void> tarea = new Task<Void>() {

            @Override
            protected Void call() throws Exception {                
                final List<UserBasic> userList = userWebClient.findAllBasics();
                final List<UserBasicFx> userFxList = convertUser(modelMapper, userList);
                userBasicList.update(userFxList);
                return null;
            }
            
        };

        tarea.setOnRunning(e -> {
            notificationControl.showLoad();
        });

        tarea.setOnSucceeded(e -> {
            notificationControl.hideLoad();
        });

        tarea.setOnFailed(e -> {
            tarea.getException().printStackTrace();
            notificationControl.showWarning(tarea.getException().getMessage());
        });

        setExecutor(createExecutor());
        return tarea;
    
    }
}
