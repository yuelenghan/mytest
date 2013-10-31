package test.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ActivitiesUtil {

	public static List<Map<String, String>> getMessage(String txtPath,
			String excelPath) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		FileInputStream fis = new FileInputStream(excelPath);

		XSSFWorkbook wb = new XSSFWorkbook(fis);
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext();) {
				Row r = rowIt.next();
				String message = FileUtil.getFileContent(txtPath);
				Map<String, String> map = new HashMap<String, String>();
				for (int j = 0; j < r.getLastCellNum(); j++) {
					Cell cell = r.getCell(j);
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
					
					if(j == 0) {
						message = message.replace("[user]", cellContent);
						map.put("userName", cellContent);
                    }
					if(j == 1) {
						message = message.replace("[card]", cellContent);
					}
					if(j == 2) {
						message = message.replace("[password]", cellContent);
						map.put("message", message);
					}
				}
				
				list.add(map);
			}
		}
		fis.close();
		return list;
	}
}
