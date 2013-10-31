package com.my;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class FileUtil {

	public static List<File> getAllFiles(File path, List<File> fileList)
			throws Exception {
		if (path.isFile()) {
			fileList.add(path);
			return fileList;
		}
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			getAllFiles(new File(files[i].getCanonicalPath()), fileList);
		}
		return fileList;
	}

	/**
	 * created by lh 读取文本文件的内容
	 * 
	 * @param fileName
	 *            文件名
	 * 
	 * @return
	 * @throws java.io.IOException
	 */
	public static String FileReaderAll(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName), "UTF-8"));
		String line = new String();
		StringBuffer temp = new StringBuffer();

		while ((line = reader.readLine()) != null) {
			temp.append(line);
		}
		reader.close();
		return temp.toString();
	}

	/**
	 * 读取word文件的内容
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String Word_03_Reader(String fileName) throws Exception {
		StringBuffer temp = new StringBuffer();

		FileInputStream fis = new FileInputStream(fileName);
		HWPFDocument doc = new HWPFDocument(fis);
		Range range = doc.getRange();
		int paragraphCount = range.numParagraphs();// 段落
		for (int i = 0; i < paragraphCount; i++) {
			// 遍历段落读取数据
			Paragraph pp = range.getParagraph(i);
			temp.append(pp.text());
		}

		fis.close();
		return getPureText(temp.toString());
	}

	public static String Word_07_Reader(String fileName) throws Exception {
		OPCPackage opcPackage = POIXMLDocument.openPackage(fileName);
		POIXMLTextExtractor ex = new XWPFWordExtractor(opcPackage);
		opcPackage.close();
		return getPureText(ex.getText());
	}

	/**
	 * 读取excel文件的内容
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String Excel_03_Reader(String fileName) throws Exception {
		StringBuffer temp = new StringBuffer();
		FileInputStream fis = new FileInputStream(fileName);

		HSSFWorkbook wb = new HSSFWorkbook(fis);

		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			HSSFSheet sheet = wb.getSheetAt(i);
			for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext();) {
				Row r = rowIt.next();
				for (Iterator<org.apache.poi.ss.usermodel.Cell> cellIt = r
						.iterator(); cellIt.hasNext();) {
					org.apache.poi.ss.usermodel.Cell cell = cellIt.next();
					String cellContent = "";

					// 如果是数字类型
					if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						cellContent = String
								.valueOf(cell.getNumericCellValue());
					}
					// 如果是字符串类型
					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						cellContent = cell.getStringCellValue();
					}

					temp.append(cellContent);
				}
			}
		}
		fis.close();
		return getPureText(temp.toString());
	}

	public static String Excel_07_Reader(String fileName) throws Exception {
		StringBuffer temp = new StringBuffer();
		FileInputStream fis = new FileInputStream(fileName);

		XSSFWorkbook wb = new XSSFWorkbook(fis);
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext();) {
				Row r = rowIt.next();
				for (Iterator<org.apache.poi.ss.usermodel.Cell> cellIt = r
						.iterator(); cellIt.hasNext();) {
					org.apache.poi.ss.usermodel.Cell cell = cellIt.next();
					String cellContent = "";

					// 如果是数字类型
					if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						cellContent = String
								.valueOf(cell.getNumericCellValue());
					}
					// 如果是字符串类型
					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						cellContent = cell.getStringCellValue();
					}

					temp.append(cellContent);
				}
			}
		}
		fis.close();
		return getPureText(temp.toString());
	}

	/**
	 * 得到纯文本
	 * 
	 * @param text
	 * @return
	 */
	public static String getPureText(String text) {
		return text.replaceAll("<([^>]*)>", "").replaceAll("\\s*|\t|\r|\n", "")
				.replaceAll("&nbsp;", "");
	}
}
