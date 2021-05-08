package tn.esprit.kidzone.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tn.esprit.kidzone.controller.UserController;

public class LoginFilter implements javax.servlet.Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		UserController employeController = (UserController)
		httpServletRequest.getSession().getAttribute("employeController");
		if (employeController!=null && employeController.getLoggedIn() != null &&
		employeController.getLoggedIn()) { chain.doFilter(request, response);}
		else {httpServletResponse.sendRedirect(httpServletRequest.getContextPath() +
		"/SignIN.jsf");}
		}
		
	}


