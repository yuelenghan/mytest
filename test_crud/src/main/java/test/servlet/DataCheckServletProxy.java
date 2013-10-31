package test.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DataCheckServletProxy extends GenericServlet {

	private static final long serialVersionUID = -4993784941472693285L;
	private String targetServlet;
	private Servlet dataCheckServlet;

	@Override
	public void init() throws ServletException {
		this.targetServlet = getInitParameter("targetServlet");
		getServletBean();
		dataCheckServlet.init(getServletConfig());
	}

	@Override
	public void service(ServletRequest req, ServletResponse resp)
			throws ServletException, IOException {
		dataCheckServlet.service(req, resp);
	}

	private void getServletBean() {
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		this.dataCheckServlet = (Servlet) wac.getBean(targetServlet);
	}

}
