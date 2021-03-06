package com.vlife.springmvc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.vlife.springmvc.Interceptor.SessionInterceptor;
import com.vlife.springmvc.converter.IdToResourceConverter;
//import com.vlife.springmvc.converter.NameToVendorEntityConverter;

//<filter>
//<filter-name>OpenSessionInViewFilter</filter-name>
//<filter-class>org.springframework.orm.hibernate3.support.OpenSessionInViewFilter</filter-class>
//</filter>
//
//<filter-mapping>
//<filter-name>OpenSessionInViewFilter</filter-name>
//<url-pattern>/*</url-pattern>
//</filter-mapping>

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.vlife.springmvc")
public class AppConfig extends WebMvcConfigurerAdapter {

//	@Autowired
//	NameToVendorEntityConverter vendorConverter;

	@Autowired
	IdToResourceConverter resourceConverter;

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
		registry.addResourceHandler("*.png").addResourceLocations("/assets/img/");
		registry.addResourceHandler("*.css").addResourceLocations("/assets/css/");
		registry.addResourceHandler("*.js").addResourceLocations("/assets/js/");
		registry.addResourceHandler("*.jsp").addResourceLocations("/WEB-INF/views/");

	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
//		registry.addConverter(vendorConverter);
		registry.addConverter(resourceConverter);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	// 拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionInterceptor())
				.excludePathPatterns("/login", "/signin", "/findpwd", "/error", "/list-apps-with-{para}",
						"/list-mobiles-by-{vendorid}", "/list-all-resources", "/list-apps-by-{vendorid}",
						"/list-all-mobiles-{vendorid}","/","/help","/trywificonnect", "/start")
				.addPathPatterns("/**");

	}
	 
	// 上传文件解析器
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		multipartResolver.setMaxUploadSize(-1);
		return multipartResolver;

	}

}
