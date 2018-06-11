package com.vlife.springmvc.configuration;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;



public class AppInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext container) throws ServletException {

		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfig.class);
		ctx.setServletContext(container);
		

		ServletRegistration.Dynamic servlet = container.addServlet(
				"dispatcher", new DispatcherServlet(ctx));

        
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		

		
		
        FilterRegistration.Dynamic filterRegistration = container.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
        filterRegistration.setInitParameter("encoding","utf-8");
        filterRegistration.setInitParameter("forceEncoding","true");
        filterRegistration.addMappingForUrlPatterns(null, false, "/");
        

	}

} 
