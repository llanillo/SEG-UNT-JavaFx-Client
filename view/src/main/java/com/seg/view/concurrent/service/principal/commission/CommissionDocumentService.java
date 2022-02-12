package com.seg.view.concurrent.service.principal.commission;

import java.util.List;
import java.util.stream.Collectors;

import com.seg.client.web.document.DocumentWebClient;
import com.seg.domain.document.dto.DocumentResponse;
import com.seg.domain.document.fx.DocumentResponseFx;
import com.seg.domain.enumeration.Role;
import com.seg.domain.observable.list.DocumentList;
import com.seg.view.concurrent.service.blueprint.DocumentService;
import com.seg.view.controller.principal.container.NotificationControl;

import org.modelmapper.ModelMapper;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommissionDocumentService extends Service<Void> implements DocumentService{

    private final DocumentWebClient documentWebclient;
    private final NotificationControl controlNotificacion;
    private final DocumentList documentList;
    private final ModelMapper modelMapper;
    private final Role role;

    @Override
    protected Task<Void> createTask() {
        final Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {      
                final List<DocumentResponse> documentResponseList = documentWebclient.findByRole(role);
                final List<DocumentResponseFx> documentFxList = documentResponseList.stream()
                                                                        .map((c) -> convertDocument(modelMapper, c))
                                                                        .collect(Collectors.toList());
                documentList.update(documentFxList);
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
