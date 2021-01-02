package com.tw.certmaster.helpers;

import com.tw.certmaster.results.RequestResultExcel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelWriter {

    private static String[] columns = {"User Name", "Certification Title", "Quarter", "Category", "Status", "Price", "Business Justification"};

    private ArrayList<RequestResultExcel> requests = new ArrayList<>();

    private Workbook workbook = new XSSFWorkbook();

    private CreationHelper createHelper = workbook.getCreationHelper();

    private Sheet sheet;

    private Font headerFont = workbook.createFont();

    private CellStyle headerCellStyle = workbook.createCellStyle();

    private ByteArrayOutputStream bos = new ByteArrayOutputStream();

    byte[] fileToBeReturnedInBytes;

    public ExcelWriter(ArrayList<RequestResultExcel> requests, String sheetName) throws IOException {
        this.requests = requests;
        this.sheet = workbook.createSheet(sheetName);
        this.headerFont.setBold(true);
        this.headerFont.setFontHeightInPoints((short) 14);
        this.headerFont.setColor(IndexedColors.RED.getIndex());
        this.headerCellStyle.setFont(headerFont);

        setHeaders();
        populateSheet();
        resizeColumns();
        buildFile();
    }

    private void populateSheet() {
        int rowNum = 1;
        for(RequestResultExcel request : requests) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(request.getUserName());

            row.createCell(1)
                    .setCellValue(request.getCertificationTitle());

            row.createCell(2)
                    .setCellValue(request.getQuarter());

            row.createCell(3)
                    .setCellValue(request.getCategoryName());

            row.createCell(4)
                    .setCellValue(request.getStatus());

            row.createCell(5)
                    .setCellValue(request.getPrice());

            row.createCell(6)
                    .setCellValue(request.getBusinessJustification());
        }
    }

    private void setHeaders() {
        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void resizeColumns() {
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void buildFile() throws IOException {
        workbook.write(bos);
        bos.close();
        workbook.close();
        fileToBeReturnedInBytes = bos.toByteArray();
    }

    public byte[] getFileToBeReturnedInBytes() {
        return fileToBeReturnedInBytes;
    }
}
