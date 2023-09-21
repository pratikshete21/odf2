package com.ecs.odf2.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUtils {
	public List<Map<String, String>> csvToListOfMaps(String path) {
		// Replace "your_csv_file.csv" with the path to your CSV file
		String csvFile = "C:\\Users\\shete\\ecs\\dispatch-system\\dispatch-system-package\\src\\main\\resources\\datastore\\migrations\\versioned\\usecase_config.csv";

		List<Map<String, String>> dataList = new ArrayList<>();

		try (FileReader reader = new FileReader(csvFile);
				CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader)) {

			for (CSVRecord csvRecord : csvParser) {
				Map<String, String> rowMap = new HashMap<>();

				// Iterate through the columns using the header names
				for (String header : csvParser.getHeaderNames()) {
					String value = csvRecord.get(header);
					rowMap.put(header, value);
				}

				dataList.add(rowMap);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print the list of maps
		for (Map<String, String> row : dataList) {
			System.out.println(row);
		}

		return dataList;
	}
}
