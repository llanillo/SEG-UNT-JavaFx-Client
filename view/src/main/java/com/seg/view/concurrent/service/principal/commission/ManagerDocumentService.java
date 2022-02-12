package com.seg.view.concurrent.service.principal.commission;

import java.util.List;
import java.util.stream.Collectors;

import com.seg.client.web.document.DocumentWebClient;
import com.seg.domain.document.dto.DocumentManage;
import com.seg.domain.document.fx.DocumentManageFx;
import com.seg.domain.observable.list.DocumentManageList;
import com.seg.view.concurrent.service.blueprint.DocumentService;
import com.seg.view.controller.principal.container.NotificationControl;

import org.modelmapper.ModelMapper;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ManagerDocumentService extends Service<Void> implements DocumentService{

    private final DocumentWebClient documentWebclient;
    private final NotificationControl controlNotificacion;
    private final DocumentManageList documentManageList;
    private final ModelMapper modelMapper;

    @Override
    protected Task<Void> createTask() {
        final Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {      
                final List<DocumentManage> documentList = documentWebclient.findAllPendingForApproval();
                final List<DocumentManageFx> documentManageFxList = documentList.stream()
                                                                            .map((c) -> convertDocument(modelMapper, c))
                                                                            .collect(Collectors.toList());
                documentManageList.update(documentManageFxList);
                return null;
            }
            
        };

        task.setOnRunning(e -> {
            controlNotificacion.showLoad();
        });

        task.setOnSucceeded(e -> {
            controlNotificacion.hideLoad();
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            controlNotificacion.showWarning(task.getException().getMessage());
        });

        setExecutor(createExecutor());
        return task;
    } 
}