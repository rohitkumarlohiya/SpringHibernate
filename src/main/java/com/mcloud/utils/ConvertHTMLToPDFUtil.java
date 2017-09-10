package com.mcloud.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class ConvertHTMLToPDFUtil {

	public static void main(String[] args) {

		String ht = "<html>\r\n" + "<p\r\n"
				+ "	style=\"width: 800px; height: 600px; padding: 20px; text-align: center; border: 10px solid #787878\">\r\n"
				+ "	<span style=\"font-size: 50px; font-weight: bold\">Certificate of\r\n"
				+ "		Completion</span> <br />\r\n"
				+ "	<br /> <span style=\"font-size: 25px\"><i>This is to certify\r\n"
				+ "			that</i></span> <br />\r\n"
				+ "	<br /> <span style=\"font-size: 30px\"><b>STUDENT_NAME</b></span><br />\r\n"
				+ "	<br /> <span style=\"font-size: 25px\"><i>has completed the\r\n"
				+ "			course</i></span> <br />\r\n"
				+ "	<br /> <span style=\"font-size: 30px\">COURSE_NAME</span> <br />\r\n"
				+ "	<br /> <span style=\"font-size: 25px\"><i>dated</i></span> <br />\r\n" + "	<br /> AWARD_DATE\r\n"
				+ "</p>\r\n" + "</html>";

		File file = new File("certificate.pdf");
		ConvertHTMLToPDFUtil.createPDF(file, ht);
	}

	public static void createPDF(File pdfFilename, String html) {

		PdfWriter pdfWriter = null;

		// create a new document
		Document document = new Document();

		try {

			// get Instance of the PDFWriter
			pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFilename));

			// document header attributes
			/*
			 * document.addAuthor("betterThanZero"); document.addCreationDate();
			 * document.addProducer(); document.addCreator("MySampleCode.com");
			 * document.addTitle("Demo for iText XMLWorker");
			 */
			document.setPageSize(PageSize.LETTER);

			// open document
			document.open();

			InputStreamReader fis = new InputStreamReader(new ByteArrayInputStream(html.getBytes()));

			// get the XMLWorkerHelper Instance
			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			// convert to PDF
			worker.parseXHtml(pdfWriter, document, fis);

			// close the document
			document.close();
			// close the writer
			pdfWriter.close();

		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

}
