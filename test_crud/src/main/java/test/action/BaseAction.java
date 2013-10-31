package test.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONObject;

import test.service.UserManager;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {
	private static final Logger logger = Logger.getLogger(BaseAction.class);

	private static final long serialVersionUID = 6690088558358957530L;
	public Map<String, Object> session;
	public HttpServletRequest request;
	public HttpServletResponse response;

	public UserManager userManager;

	public void writeToWindow(String s) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(s);
		} catch (IOException e) {
			logger.error("writeToWindow(String)", e);
		}
	}
	
	public void writeJsonToWindow(JSONObject json) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			logger.error("writeToWindow(String)", e);
		}
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
