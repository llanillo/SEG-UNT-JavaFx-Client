package com.seg.view.concurrent.service.blueprint;

import com.seg.domain.document.dto.DocumentManage;
import com.seg.domain.document.dto.DocumentResponse;
import com.seg.domain.document.fx.DocumentManageFx;
import com.seg.domain.document.fx.DocumentResponseFx;

import org.modelmapper.ModelMapper;

public interface DocumentService extends IService{      

    default DocumentResponseFx convertDocument(final ModelMapper modelMapper, final DocumentResponse documentResponse){        
        return modelMapper.map(documentResponse, DocumentResponseFx.class);
    }

    default DocumentManageFx convertDocument(final ModelMapper modelMapper, final DocumentManage documentManage){        
        return modelMapper.map(documentManage, DocumentManageFx.class);
    }
}
