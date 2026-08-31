package com.asif.sdet.banking.unit;

import com.asif.sdet.banking.utils.ExcelUtils;
import org.junit.Assert;
import org.junit.Test;

public class ExcelUtilsTest {

    @Test
    public void readLoginDataFromExcel() {

        ExcelUtils excel = new ExcelUtils(
                "src/test/resources/testdata/BankingTestData.xls",
                "LoginData"
        );

        String username = excel.getCellData(1, 0);
        String password = excel.getCellData(1, 1);

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        Assert.assertEquals("asif", username);
        Assert.assertEquals("Bank123!", password);

        excel.closeWorkbook();
    }

    @Test
    public void writeResultToExcel() {

        ExcelUtils excel = new ExcelUtils(
                "src/test/resources/testdata/BankingTestData.xls",
                "LoginData"
        );

        excel.setCellData(1, 2, "PASSED");

        excel.closeWorkbook();
    }
}