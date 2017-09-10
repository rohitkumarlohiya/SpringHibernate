package com.mcloud.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class ItextHtmlToPDFUtil {

	public static void convert(String html, File file) {

		try {
			OutputStream out = new FileOutputStream(file);
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			StringBuilder htmlString = new StringBuilder();
			htmlString.append(html);
			document.open();
			InputStream is = new ByteArrayInputStream(htmlString.toString().getBytes());
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
			document.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
