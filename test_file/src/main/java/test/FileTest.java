package test;

import java.util.List;
import java.util.Map;

import test.util.ActivitiesUtil;

public class FileTest {
	public static void main(String[] args) {
		String messagePath = "D:/message.txt";
		String excelPath = "D:/user.xlsx";
		
		try {
			List<Map<String, String>> list = ActivitiesUtil.getMessage(messagePath, excelPath);
			
			for(Map<String, String> map : list) {
				System.out.println("=====给用户"+map.get("userName") + "发送短信=====");
				System.out.println("=====短信内容："+map.get("message"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
