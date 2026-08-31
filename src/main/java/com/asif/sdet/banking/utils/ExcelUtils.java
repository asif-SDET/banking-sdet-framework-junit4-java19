package com.asif.sdet.banking.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;

public class ExcelUtils {

    private Workbook workbook;
    private Sheet sheet;
    private String filePath;

    public ExcelUtils(String filePath, String sheetName) {
        this.filePath = filePath;

        try {
            FileInputStream inputStream = new FileInputStream(filePath);

            workbook = new HSSFWorkbook(inputStream);
            sheet = workbook.getSheet(sheetName);

            inputStream.close();

        } catch (IOException e) {
            throw new RuntimeException("Unable to open Excel file", e);
        }
    }

    // READ
    public String getCellData(int rowNumber, int columnNumber) {

        Row row = sheet.getRow(rowNumber);
        Cell cell = row.getCell(columnNumber);

        return cell.toString();
    }

    // WRITE
    public void setCellData(int rowNumber,
                            int columnNumber,
                            String value) {

        Row row = sheet.getRow(rowNumber);

        if (row == null) {
            row = sheet.createRow(rowNumber);
        }

        Cell cell = row.getCell(columnNumber);

        if (cell == null) {
            cell = row.createCell(columnNumber);
        }

        cell.setCellValue(value);

        try (FileOutputStream outputStream =
                     new FileOutputStream(filePath)) {

            workbook.write(outputStream);

        } catch (IOException e) {
            throw new RuntimeException("Unable to write to Excel file", e);
        }
    }

    public void closeWorkbook() {

        try {
            workbook.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}