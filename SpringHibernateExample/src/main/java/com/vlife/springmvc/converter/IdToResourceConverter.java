package com.vlife.springmvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.vlife.springmvc.model.Resources;
import com.vlife.springmvc.model.Vendor;
import com.vlife.springmvc.service.ResourceService;
import com.vlife.springmvc.service.VendorService;

@Component
public class IdToResourceConverter implements Converter<Object, Resources> {

	@Autowired
	ResourceService resources_service;

	/**
	 * Gets UserProfile by Id
	 * 
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public Resources convert(Object element) {
		Integer id = Integer.parseInt((String) element);
		Resources resource = resources_service.findById(id);
		return resource;
	}

}

