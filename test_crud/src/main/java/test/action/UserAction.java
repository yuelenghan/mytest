package test.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import test.model.User;
import test.util.DateUtil;
import test.util.JsonUtil;
import test.util.StringUtil;

public class UserAction extends BaseAction {
	private static final long serialVersionUID = 8969001276517035826L;

	private static final Logger logger = Logger.getLogger(UserAction.class);

	private long id;
	private String userName;
	private int age;
	private String phone;
	private String address;
	private String start;
	private String limit;

	public String createUser() {
		User user = new User();
		user.setUserName(userName);
		user.setAge(age);
		user.setPhone(phone);
		user.setRdate(new Date());

		long userId = 0;

		try {
			userId = userManager.createUser(user);
			if (logger.isInfoEnabled()) {
				logger.info("createUser() - long userId=" + userId);
			}
		} catch (Exception e) {
			logger.error("createUser()", e);
			return "error";
		}

		return "success";
	}

	public void getData() {
		int startInt = 0;
		int limitInt = 20;
		if (!StringUtil.isNullStr(start)) {
			startInt = Integer.parseInt(start);
		}
		if (!StringUtil.isNullStr(limit)) {
			limitInt = Integer.parseInt(limit);
		}
		List<User> userList = userManager.queryUser(startInt, limitInt);
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for (User u : userList) {
			String id = String.valueOf(u.getId());
			String userName = u.getUserName();
			String age = String.valueOf(u.getAge());
			String phone = u.getPhone();
			String date = DateUtil.dateToString(u.getRdate());

			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("userName", userName);
			map.put("age", age);
			map.put("phone", phone);
			map.put("date", date);
			resultList.add(map);
		}
		long resultCount = userManager.getUserCount();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultCount", resultCount);
		resultMap.put("resultList", resultList);
		try {
			JSONObject json = JsonUtil.mapToJson(resultMap);
			this.writeJsonToWindow(json);
		} catch (JSONException e) {
			logger.error(e);
		}
	}

	public void queryUser() {
		User user = userManager.getUserByPhone(phone);
		if (user != null) {
			List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
			// long resultCount = userManager.getUserCount();
			String id = String.valueOf(user.getId());
			String userName = user.getUserName();
			String age = String.valueOf(user.getAge());
			String phone = user.getPhone();
			String date = DateUtil.dateToString(user.getRdate());

			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("userName", userName);
			map.put("age", age);
			map.put("phone", phone);
			map.put("date", date);
			resultList.add(map);

			Map<String, Object> resultMap = new HashMap<String, Object>();
			// resultMap.put("resultCount", resultCount);
			resultMap.put("resultList", resultList);
			try {
				JSONObject json = JsonUtil.mapToJson(resultMap);
				this.writeJsonToWindow(json);
			} catch (JSONException e) {
				logger.error(e);
			}
		}
	}

	public String deleteUser() {
		try {
			userManager.deleteUser(userManager.getUser(id));
			return "success";
		} catch (Exception e) {
			logger.error(e);
			return "error";
		}
	}

	public String updateUser() {
		try {
			User user = userManager.getUser(id);
			user.setAge(age);
			user.setPhone(phone);
			userManager.updateUser(user);
			return "success";
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return "error";
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
