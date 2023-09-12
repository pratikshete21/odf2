package com.wf.uc.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExcelUtils {

	public static void convertListofMapsToExcel(List<Map<String, String>> dataList, String filePath)
			throws IOException {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Report");

			// Create the header row
			Row headerRow = sheet.createRow(0);
			int colNum = 0;
			for (String key : dataList.get(0).keySet()) {
				Cell cell = headerRow.createCell(colNum++);
				cell.setCellValue(key);
			}

			// Create data rows
			int rowNum = 1;
			for (Map<String, String> data : dataList) {
				Row row = sheet.createRow(rowNum++);
				colNum = 0;
				for (String value : data.values()) {
					Cell cell = row.createCell(colNum++);
					cell.setCellValue(value);
				}
			}

			// Write the workbook to the file
			try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
				workbook.write(outputStream);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new LinkedHashMap<>();
		map.put("name", "dilip");
		map.put("age", "2");
		dataList.add(map);

		Map<String, String> map2 = new LinkedHashMap<>();
		map2.put("name", "pratik");
		map2.put("age", "1");
		dataList.add(map2);

		/* Your list of maps */;
		Path temp = Files.createTempFile(Files.createTempDirectory("tempDir"), UUID.randomUUID().toString(), ".xlsx");
		String filePath = "C:\\Users\\shete\\Downloads\\output.xlsx";
		convertListofMapsToExcel(dataList, temp.toString());
		System.out.println(temp.toString());
		System.out.println(Files.readAllBytes(temp));
		Files.deleteIfExists(temp);
	}
}
