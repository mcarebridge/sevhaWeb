package com.phr.ade.controller.health;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class WebFilter implements Filter {

	private static Logger logger = Logger.getLogger(WebFilter.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		logger.log(Level.INFO, "WebFilter.init --->--");
		System.out.println("WebFilter.init --->--");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest _httprequest = (HttpServletRequest) request;
		String serverName = _httprequest.getServerName();
		logger.log(Level.INFO, "serverName --->" + serverName);
		ServletContext context = _httprequest.getSession().getServletContext();
		
		if(serverName.contains("sevha.com")) {
			serverName = "sevh" + '\u0101';		
		}else if(serverName.contains("usa.care")) {
			serverName = "usa.care";
		}
		else if(serverName.contains("dubaihealth.care")) {
			serverName = "dubaihealth.care";
		}
		
		context.setAttribute("serverName", serverName);
		
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.log(Level.INFO, "WebFilter.destroy --->");

	}

}
