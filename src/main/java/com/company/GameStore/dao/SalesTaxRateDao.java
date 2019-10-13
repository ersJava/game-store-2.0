package com.company.GameStore.dao;

import com.company.GameStore.model.SalesTaxRate;

public interface SalesTaxRateDao {

    SalesTaxRate getSalesTaxRate(String state);

}
