package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.Vendor;

public interface VendorService {
	Vendor findById(int id);

	void saveVendor(Vendor vendor);

	void deleteVendorByID(int id);

	List<Vendor> findAllVendor();

	void updateVendor(Vendor vendor);

}
