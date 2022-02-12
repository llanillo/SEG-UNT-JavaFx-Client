package com.seg.view.concurrent.service.principal.general;

import com.seg.client.web.document.DocumentWebClient;
import com.seg.domain.document.dto.DocumentProperties;
import com.seg.domain.document.dto.DocumentResponse;
import com.seg.domain.document.fx.DocumentResponseFx;
import com.seg.domain.observable.list.DocumentList;
import com.seg.view.concurrent.service.blueprint.DocumentService;
import com.seg.view.controller.principal.container.NotificationControl;

import org.modelmapper.ModelMapper;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UploadDocumentService extends Service<Void> implements DocumentService {

    private final DocumentWebClient documentWebClient;
    private final NotificationControl notificationControl;
    private final DocumentProperties documentProperties;
    private final DocumentList documentList;
    private final ModelMapper modelMapper;

    @Override
    protected Task<Void> createTask() {
        final Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                try{
                    final DocumentResponse documentResponse = documentWebClient.uploadDocument(documentProperties);
                    final DocumentResponseFx documentResponseFx = convertDocument(modelMapper, documentResponse);
                    documentList.add(documentResponseFx);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return null;
            }
            
        };

        task.setOnRunning(e -> {
            notificationControl.showLoad();
        });

        task.setOnSucceeded(e -> {
            notificationControl.hideLoad();
        });

        task.setOnFailed(e -> {
            notificationControl.showWarning(task.getException().getMessage());
        });

        return task;
    }
    
}
