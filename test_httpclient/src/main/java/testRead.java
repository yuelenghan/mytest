import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-25
 * Time: 下午4:02
 * To change this template use File | Settings | File Templates.
 */
public class testRead {
    public static void main(String[] args) {
        try {
            List<Map<String, String>> list = ReadExcel.Excel_03_Reader("d:/test.xls");
            // System.out.println(list.size());
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = list.get(i);
//                System.out.println(map.get("name"));
//                System.out.println(map.get("idCard"));
//                System.out.println(map.get("carType"));
//                System.out.println(map.get("unit"));
//                System.out.println(map.get("phone"));
//                System.out.println(map.get("date"));
//                System.out.println(map.get("addr"));
                String idCard = map.get("idCard");
                int num = Integer.parseInt(String.valueOf(idCard.charAt(idCard.length() - 2)));
                System.out.println(num);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
