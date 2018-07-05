package com.vlife.springmvc.dao;

import java.util.List;
import com.vlife.springmvc.model.Vendor;

public interface VendorDao {

	Vendor findById(int id);

	void saveVendor(Vendor vendor);

	List<Vendor> findAllVendor();

	void deleteVendorByID(int id);

}
