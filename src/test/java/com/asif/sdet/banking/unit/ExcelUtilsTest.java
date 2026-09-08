package com.asif.sdet.banking.unit;

import com.asif.sdet.banking.utils.ExcelUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void writeResultToExcelNewRow() {

        ExcelUtils excel = new ExcelUtils(
                "src/test/resources/testdata/BankingTestData.xls",
                "LoginData"
        );

        excel.setCellData(2, 0, "newuser");
        excel.setCellData(2, 1, "NewPass123!");
        excel.setCellData(2, 2, "FAILED!");

        excel.closeWorkbook();
    }

    @Test
    public void getUsernamesFromExcel() {
        ExcelUtils excel = new ExcelUtils("src/test/resources/testdata/BankingTestData.xls",
                "LoginData");

        List<String> usernames = excel.getColumnData(0);

        System.out.println("Usernames: " + usernames);
       excel.closeWorkbook();

    }


    public void getNotesFromAllSheets(String columnName, String sheetName, String filePath) {

        ExcelUtils excel = new ExcelUtils("src/test/resources/testdata/" + filePath,
                sheetName);
        List<String> notes = excel.getColumnDataByHeader(sheetName, columnName);

        for (String note: notes){
            System.out.println("Note: " + note);
        }
        excel.closeWorkbook();


    }
}