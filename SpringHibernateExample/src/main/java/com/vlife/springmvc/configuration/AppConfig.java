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
import com.vlife.springmvc.converter.NameToVendorEntityConverter;;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.vlife.springmvc")
public class AppConfig extends WebMvcConfigurerAdapter {

	@Autowired
	NameToVendorEntityConverter vendorConverter;

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
		registry.addConverter(vendorConverter);
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
		/*registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/applicationlist-{page}-{vendorid}",
				"/themelist-{page}", "newtheme", "/newtheme-{type}","");*/
		registry.addInterceptor(new SessionInterceptor()).excludePathPatterns("/login","/signin").addPathPatterns("/**");
		
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
