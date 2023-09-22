package com.ecs.odf2.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

<!-- https://mvnrepository.com/artifact/com.itextpdf/itextpdf -->
		<dependency>
			<groupId>com.itextpdf</groupId>
			<artifactId>itextpdf</artifactId>
			<version>5.5.13.3</version>
		</dependency>

public class ScreenshotsToPdfConverter {

	public void convertScreenshotsToPdf(List<byte[]> screenshotList, String outputPdfPath) {
		try {
			Document document = new Document();

			PdfWriter.getInstance(document, new FileOutputStream(outputPdfPath));
			document.open();

			for (byte[] screenshotByteArray : screenshotList) {
				BufferedImage awtImage = ImageIO.read(new ByteArrayInputStream(screenshotByteArray));

				int imageWidth = awtImage.getWidth();
				int imageHeight = awtImage.getHeight();

				// Create a page with the dimensions of the screenshot
				document.setPageSize(new com.itextpdf.text.Rectangle(imageWidth, imageHeight));

				// Convert the AWT image to an iText Image
				Image pdfImage = Image.getInstance(awtImage, null);
				document.newPage();
				// Add the image to the PDF
				document.add(pdfImage);

				document.newPage(); // Add a new page for the next screenshot
			}

			document.close();
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		List<byte[]> screenshotList = Arrays.asList(Files.readAllBytes(Paths.get("C:\\Users\\shete\\Downloads\\1.png")),
				Files.readAllBytes(Paths.get("C:\\Users\\shete\\Downloads\\2.png")),
				Files.readAllBytes(Paths.get("C:\\Users\\shete\\Downloads\\3.png")));
		/* Your list of screenshot byte arrays */;
		String outputPdfPath = System.getProperty("user.home") + File.separator + "imagep1df.pdf";

		ScreenshotsToPdfConverter converter = new ScreenshotsToPdfConverter();
		converter.convertScreenshotsToPdf(screenshotList, outputPdfPath);

		System.out.println("PDF created successfully.");
	}
}
