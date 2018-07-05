package com.vlife.springmvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.vlife.springmvc.model.Vendor;
import com.vlife.springmvc.service.VendorService;

@Component
public class NameToVendorEntityConverter implements Converter<Object, Vendor> {

	@Autowired
	VendorService vendor_service;

	/**
	 * Gets UserProfile by Id
	 * 
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public Vendor convert(Object element) {
		Integer id = Integer.parseInt((String) element);
		Vendor vendor = vendor_service.findById(id);
		return vendor;
	}

}
