package com.example.web_spring_security_demo.services;

import com.example.web_spring_security_demo.payload.request.SignupRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ExcelService {
    //Create workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException{
        Workbook workbook = null;
        if(excelFilePath.endsWith("xlsx")){
            workbook = new XSSFWorkbook();
        }else if(excelFilePath.endsWith("xls")){
            workbook = new HSSFWorkbook();
        }else{
            throw  new IllegalArgumentException("The specified file is not Exception");
        }
        return workbook;
    }
    public void processExcelFile(MultipartFile file) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = 0;
        for (Row row : sheet) {
            if (rowCount == 0) {
                rowCount++;
                continue;
            }
            Cell cell1 = row.getCell(0);
            if (cell1 == null || cell1.getCellType() == CellType.BLANK) {
                return;
            }
            String username = cell1.getStringCellValue();
            String email = row.getCell(1).getStringCellValue();
            String password = String.valueOf(row.getCell(2).getNumericCellValue());
            String roles = row.getCell(3).getCellType() == CellType.BLANK || row.getCell(3) ==null ? "" : row.getCell(3).getStringCellValue();

            System.out.println(username);
            System.out.println(email);
            System.out.println(password);
            System.out.println(roles);


            // Kiểm tra nếu cột 5 (index 4) đã tồn tại trong hàng
            Cell cell6 = row.getCell(5);
            if (cell6 == null) {
                // Nếu ô chưa tồn tại, tạo một ô mới
                cell6 = row.createCell(5);
            }
            // Ghi dữ liệu mới vào ô
            cell6.setCellValue("New value");
        }
        // Lưu lại workbook

    }

}
