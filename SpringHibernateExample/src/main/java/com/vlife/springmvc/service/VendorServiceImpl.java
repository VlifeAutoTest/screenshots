package com.vlife.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.VendorDao;
import com.vlife.springmvc.model.Vendor;

@Service("vendorService")
@Transactional
public class VendorServiceImpl implements VendorService {

	@Autowired
	private VendorDao dao;

	public Vendor findById(int id) {
		return dao.findById(id);
	}

	public void saveVendor(Vendor Vendor) {
		dao.saveVendor(Vendor);
	}

	public void deleteVendorByID(int id) {
		dao.deleteVendorByID(id);
	}

	public List<Vendor> findAllVendor() {
		return dao.findAllVendor();
	}

	public void updateVendor(Vendor vendor) {
		Vendor entity = dao.findById(vendor.getId());
		if (entity != null) {
			entity.setName(vendor.getName());

		}
	}

}
