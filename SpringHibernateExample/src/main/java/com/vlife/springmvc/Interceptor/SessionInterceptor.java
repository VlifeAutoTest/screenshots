package com.vlife.springmvc.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO 自动生成的方法存根
		String tvendorid = (String) request.getSession().getAttribute("tvendorid");
		String searchValue = (String) request.getSession().getAttribute("searchValue");
		String pageType = (String) request.getSession().getAttribute("pageType");
		
		if (tvendorid == null || searchValue == null || pageType==null ) {
			request.getRequestDispatcher("/list").forward(request, response);
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
