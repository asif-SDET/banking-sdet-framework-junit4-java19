package com.asif.sdet.banking.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getColumnData(int columnNumber) {

        List<String> values = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            values.add(getCellData(i, columnNumber));
        }

        return values;
    }

    public List<String> getColumnDataByHeader(String sheetName, String columnName) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getSheetName().equalsIgnoreCase(sheetName)) {
                Row headerRow = sheet.getRow(0);
                for (Cell cell : headerRow) {
                    if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                        int columnIndex = cell.getColumnIndex();
                        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                            Row row = sheet.getRow(j);
                            Cell noteCell = row.getCell(columnIndex);
                            if (noteCell != null) {
                                values.add(noteCell.toString());
                            } else {
                                values.add("");
                            }
                        }
                    }
                }
            }
        }
        return values;
    }

    public void closeWorkbook() {

        try {
            workbook.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}