import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-25
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class ReadExcel {
    /**
     * 读取03版excel文件的内容
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> Excel_03_Reader(String fileName) throws Exception {
        StringBuffer temp = new StringBuffer();
        FileInputStream fis = new FileInputStream(fileName);

        HSSFWorkbook wb = new HSSFWorkbook(fis);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            HSSFSheet sheet = wb.getSheetAt(i);
            for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext(); ) {
                Map<String, String> map = new HashMap<String, String>();
                int j = 0;
                Row r = rowIt.next();
                for (Iterator<org.apache.poi.ss.usermodel.Cell> cellIt = r
                        .iterator(); cellIt.hasNext(); ) {
                    org.apache.poi.ss.usermodel.Cell cell = cellIt.next();
                    String cellContent = "";

                    //如果是数字类型
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        //如果是日期类型
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            cellContent = sdf.format(cell.getDateCellValue());
                        } else {
                            cellContent = String
                                    .valueOf(cell.getNumericCellValue());
                        }
                    }
                    //如果是字符串类型
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        cellContent = cell.getStringCellValue();
                    }

                    if (j == 0) {
                        map.put("name", cellContent);
                    }
                    if (j == 1) {
                        map.put("idCard", cellContent);
                    }
                    if (j == 2) {
                        map.put("carType", cellContent);
                    }
                    if (j == 3) {
                        map.put("unit", cellContent);
                    }
                    if (j == 4) {
                        map.put("phone", cellContent);
                    }
                    if (j == 5) {
                        map.put("date", cellContent);
                    }
                    if (j == 6) {
                        map.put("addr", cellContent);
                    }


                    //temp.append(cellContent);
                    j++;
                }
                list.add(map);
            }
        }
        fis.close();
        System.out.println(temp);
        return list;
    }
}
