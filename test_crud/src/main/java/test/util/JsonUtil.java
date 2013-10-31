package test.util;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

	public static JSONObject listToJson(List<?> list) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("returnList", list);
		return json;
	}
	
	public static JSONObject mapToJson(Map<?,?> map) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("returnMap", map);
		return json;
	}
	
}
