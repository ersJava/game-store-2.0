package com.company.GameStore.dao;

import com.company.GameStore.model.SalesTaxRate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SalesTaxRateDaoJdbcTemplateImplTest {

    @Autowired
    SalesTaxRateDao salesTaxRateDao;

    @Test
    public void getSalesTaxRate() {

        SalesTaxRate str = new SalesTaxRate();
        str.setState("NY");
        str.setRate(new BigDecimal(".06"));

        SalesTaxRate str2 = salesTaxRateDao.getSalesTaxRate("NY");
        assertEquals(str2, str);
    }
}