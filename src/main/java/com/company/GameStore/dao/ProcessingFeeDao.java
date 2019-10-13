package com.company.GameStore.dao;

import com.company.GameStore.model.ProcessingFee;

public interface ProcessingFeeDao {

    ProcessingFee getProcessingFee(String itemType);

}
