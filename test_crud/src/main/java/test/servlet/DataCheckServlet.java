package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import test.service.UserManager;


@Component
public class DataCheckServlet extends GenericServlet implements Servlet{
	private static final Logger logger = Logger
			.getLogger(DataCheckServlet.class);

	private static final long serialVersionUID = -7668400715966989899L;
	public UserManager userManager;

	@Override
	public void service(ServletRequest servletrequest,
			ServletResponse servletresponse) throws ServletException,
			IOException {
		String userName = servletrequest.getParameter("userName");
		if (logger.isInfoEnabled()) {
			logger.info("doPost(HttpServletRequest, HttpServletResponse) - String userName=" + userName); //$NON-NLS-1$
		}

		try {
			if (userManager.ifUserExists(userName)) {
				this.write(servletresponse, "该用户名已经被注册", "error");
			} else {
				this.write(servletresponse, "用户名可用", "success");
			}
		} catch (IOException e) {
			logger.error("doPost(HttpServletRequest, HttpServletResponse)", e); 
		} catch (JSONException e) {
			logger.error("doPost(HttpServletRequest, HttpServletResponse)", e); 
		}
		
	}
	
	private void write(ServletResponse servletresponse, String content, String type)
			throws IOException, JSONException {
		servletresponse.setCharacterEncoding("UTF-8");
		PrintWriter writer = servletresponse.getWriter();

		String style = "";
		if (type.equals("success")) {
			style = "color: green;";
		} else {
			style = "color: red;";
		}

		JSONObject json = new JSONObject();
		json.put("content", content);
		json.put("style", style);

		writer.println(json);
	}

	@Resource
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
