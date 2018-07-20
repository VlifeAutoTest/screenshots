package com.vlife.springmvc.Interceptor;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.vlife.springmvc.model.Resources;
import com.vlife.springmvc.model.Role;
import com.vlife.springmvc.model.User;

public class SessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO 自动生成的方法存根
		User user = (User) request.getSession().getAttribute("logSuccessUser");
		String tvendorid = (String) request.getSession().getAttribute("tvendorid");
		String searchValue = (String) request.getSession().getAttribute("searchValue");
		String pageType = (String) request.getSession().getAttribute("pageType");
		String path = request.getServletPath();
		String method = request.getMethod().toLowerCase().trim();
		System.out.println("进入拦截器了-------------------------------------");
		
		if (user == null ) {
			request.getRequestDispatcher("/login").forward(request, response);
			return false;
		}
		

		else if (!path.equals("/logout")) {

			Role role = user.getRole();
			Set<Resources> set = role.getRelresources();
			Boolean boo = false;
			for (Resources res : set) {
				String value = res.getResource();
				String requestMethod = res.getMethod().trim();
				if (requestMethod.equals(method)) {

					if (value.contains("{page}")) {
						value = value.replace("{page}", "\\d+");
					}
					if (value.contains("{type}")) {
						value = value.replace("{type}", "\\d+");
					}
					if (value.contains("{id}")) {
						value = value.replace("{id}", "\\d+");
					}
					if (value.contains("{uid}")) {
						value = value.replace("{uid}", "[A-Za-z0-9]+");
					}
					if (value.contains("{ssn}")) {
						value = value.replace("{ssn}", "[A-Za-z0-9]+");
					}
					if (value.contains("{vendorid}")) {
						value = value.replace("{vendorid}", "\\d+");

					}
					if (value.contains("d+") || value.contains("[A-Za-z0-9]+")) {
						Pattern pattern = Pattern.compile(value);
						Matcher matcher = pattern.matcher(path);
						boo = matcher.matches();
						if (boo == true) {
							break;
						}
					}

					else if (res.getResource().trim().equals(path)) {
						boo = true;
						break;
					}

				}

			}

			if (boo == false) {
				request.getRequestDispatcher("/error").forward(request, response);
				return false;
			}

		}

		if (tvendorid == null || searchValue == null || pageType == null) {
			request.getRequestDispatcher("/query").forward(request, response);
			return false;
		}

		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO 自动生成的方法存根

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO 自动生成的方法存根

	}

}
