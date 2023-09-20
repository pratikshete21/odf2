package com.ecs.odf2.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ecs.odf2.entity.ExcelDetails;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
	public void getDataFromExcel(String excelPath) {
//		List<Map<String, Object>> dataList = new ArrayList<>();
		List<ExcelDetails> excelDataList = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(excelPath); Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheetAt(0);

			// Get the headers (assuming the first row contains column names)
			Row headerRow = sheet.getRow(0);
			List<String> columnNames = new ArrayList<>();
			for (Cell cell : headerRow) {
				columnNames.add(cell.getStringCellValue());
			}

			// Loop through the data rows and create maps
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row dataRow = sheet.getRow(rowIndex);
//				Map<String, Object> rowData = new HashMap<>();
				ExcelDetails excelDetails = new ExcelDetails();

				for (int colIndex = 0; colIndex < columnNames.size(); colIndex++) {
					Cell cell = dataRow.getCell(colIndex);

					// Determine the data type and store accordingly
					if (cell != null) {
						switch (cell.getCellType()) {
						case STRING:
							setExcelDetails(columnNames.get(colIndex), cell.getStringCellValue(), excelDetails);
							break;
						case NUMERIC:
							setExcelDetails(columnNames.get(colIndex), cell.getNumericCellValue(), excelDetails);
							break;
						case BOOLEAN:
							setExcelDetails(columnNames.get(colIndex), cell.getBooleanCellValue(), excelDetails);
							break;
						case BLANK:
							setExcelDetails(columnNames.get(colIndex), null, excelDetails);
							break;
						default:
							setExcelDetails(columnNames.get(colIndex), null, excelDetails);
						}
					} else {
						setExcelDetails(columnNames.get(colIndex), null, excelDetails);
					}
				}

				excelDataList.add(excelDetails);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print the list of maps
		for (ExcelDetails row : excelDataList) {
			System.out.println(row.toString());
		}
	}

	public static void setExcelDetails(String columnName, Object cellValue, ExcelDetails excelDetails) {
		if (columnName.toLowerCase().contains("mobile")) {
			excelDetails.setMobile(String.valueOf(Math.round((double) cellValue)));
		} else if (columnName.toLowerCase().contains("order")) {
			excelDetails.setOrder(String.valueOf(cellValue));
		} else if (columnName.toLowerCase().contains("delivery note")) {
			excelDetails.setNote(String.valueOf(cellValue));
		} else if (columnName.toLowerCase().contains("customer id")) {
			excelDetails.setCustomerId(String.valueOf(Math.round((double) cellValue)));
		} else if (columnName.toLowerCase().contains("order date")) {
			excelDetails.setOrderDate(String.valueOf(cellValue));
		} else if (columnName.toLowerCase().contains("dispatch date")) {
			excelDetails.setDispatchDate(String.valueOf(cellValue));
		} else if (columnName.toLowerCase().contains("emailid")) {
			excelDetails.setMailId(String.valueOf(cellValue));
		} else if (columnName.toLowerCase().contains("delivery status")) {
			excelDetails.setStatus(String.valueOf(cellValue));
		} else if (columnName.toLowerCase().contains("customer name")) {
			excelDetails.setCustomerName(String.valueOf(cellValue));
		}
	}

	public static void main(String[] args) {
		ExcelUtils excelUtils = new ExcelUtils();
		excelUtils.getDataFromExcel("C:\\Users\\shete\\Downloads\\dispatch.xlsx");
	}
}
