package com.seg.domain.document.blueprint;

import com.seg.domain.commission.dto.CommissionSummary;

public interface IDocumentManage extends IDocumentResponse{
    
    CommissionSummary getCommission();
    void setCommission(CommissionSummary commission);
}
