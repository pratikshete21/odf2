package com.wf.uc.utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * @author shete
 *
 */
public class CommonUtils {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/*
		 * try { System.out.println(readPdfAsText(Files.readAllBytes(Paths.
		 * get("C:\\Users\\shete\\OneDrive\\Desktop\\Documents\\Screenshot 2023-07-24 213108.png"
		 * )))); } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * try { System.out.println(readPdfAsText(takeScreenshot())); } catch (Exception
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		
		List<Map<String, String>> csvData= new ArrayList<Map<String, String>>();
		Map<String, String> e= new LinkedHashMap<>();
		e.put("my ,name", "pratik");
		e.put("surname", "shete, ");
		csvData.add(e);
		System.out.println(createCsvFileWithList(csvData));
		

	}

	/**
	 * This method return text from pdf
	 * 
	 * @param pdfByte
	 * @return String text
	 * @throws Exception
	 */
	public static String readPdfAsText(byte[] pdfByte) throws Exception {

		String text;
		try {
			// Loading an existing document
			PDDocument document = PDDocument.load(pdfByte);
			// Instantiate PDFTextStripper class
			PDFTextStripper pdfStripper = new PDFTextStripper();
			// Retrieving text from PDF document
			text = pdfStripper.getText(document);
			// Closing the document
			document.close();
		} catch (Exception e) {
			text = "";
		}
		return text;
	}

	/**
	 * This method return the screenshot in byte[]
	 * 
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] takeScreenshot() throws Exception {
		byte[] byteArray;
		try {

			Thread.sleep(120);
			Robot r = new Robot();

			UUID uuid = UUID.randomUUID();
			String pathString = System.getProperty("user.home") + File.separator + uuid + ".png";
			Path path = Paths.get(pathString);

			Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage image = r.createScreenCapture(capture);

			ImageIO.write(image, "png", new File(pathString));
			byteArray = Files.readAllBytes(path);
			Files.deleteIfExists(path);
			System.out.println("ScreenShot Byte: not null");

		} catch (Exception e) {
			byteArray = null;
			System.out.println("ScreenShot Byte: " + null);
		}
		return byteArray;
	}

	/**
	 * This method copy all xml bot files from target/classes to resourse folder
	 * 
	 * @throws Exception
	 */
	public static void copyXmlBotFilesInResourseFolder() throws Exception {

		FileUtils.copyDirectory(new File("target/classes/configs/main"), new File("src/main/resources/configs/main"));
	}

	/**
	 * This method creates csv file from list of maps
	 * 
	 * @param List<Map<String,String>>csvData
	 * @return csvFilePath in String
	 */
	public static String createCsvFileWithList(List<Map<String, String>> csvData) {

		UUID uuid = UUID.randomUUID();
		String filePath = System.getProperty("user.home") + File.separator + uuid + ".csv";
		try {
			FileWriter writer = new FileWriter(filePath);
			String header = "";
			for (String key : csvData.get(0).keySet()) {
				header = header + "\"" + key + "\"" + ",";
			}
			header = header + "\n";

			writer.append(header);

			for (int i = 0; i < csvData.size(); i++) {
				String row = "";
				for (String key : csvData.get(0).keySet()) {
					row = row + "\"" + csvData.get(i).get(key) + "\",";
				}
				row = row + "\n";
				writer.append(row);
			}

			writer.close();

		} catch (Exception e) {
			return "";
		}
		return filePath;
	}
	
	public static String sendGetRequest(String url, String username, String password) throws IOException {
	    URL urlObj = new URL(url);
	    HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
	    
	    // Set up the HTTP GET request
	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Authorization", "Basic " + getBase64Auth(username, password));
	    
	    int responseCode = connection.getResponseCode();
	    
	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        // Read the response
	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String inputLine;
	        StringBuilder response = new StringBuilder();
	        
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        
	        return response.toString();
	    } else {
	        throw new IOException("HTTP GET request failed with error code: " + responseCode);
	    }
	}

	public static String getBase64Auth(String username, String password) {
	    String userpass = username + ":" + password;
	    return java.util.Base64.getEncoder().encodeToString(userpass.getBytes());
	}

}
