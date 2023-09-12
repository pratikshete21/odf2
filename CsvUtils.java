package com.wf.uc.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvUtils {
	
	/*<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-csv</artifactId>
    <version>1.8</version>
    </dependency>*/


	public static void convertListofMapsToCSV(List<Map<String, String>> dataList, String filePath) throws IOException {
		try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(filePath), CSVFormat.DEFAULT)) {

			// Print the header row
			if (!dataList.isEmpty()) {
				Map<String, String> firstRow = dataList.get(0);
				csvPrinter.printRecord(firstRow.keySet());
			}

			// Print the data rows
			for (Map<String, String> data : dataList) {
				csvPrinter.printRecord(data.values());
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
		String filePath = "C:\\Users\\shete\\Downloads\\output.csv";
		convertListofMapsToCSV(dataList, filePath);
	}
}
