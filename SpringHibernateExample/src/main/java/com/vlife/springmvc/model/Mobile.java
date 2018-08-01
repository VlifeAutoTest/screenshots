package com.vlife.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "mobile")
public class Mobile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Size(min = 1, max = 32)
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotEmpty
	@Column(name = "uid", nullable = false,unique = true)
	private String uid;

	@Size(min = 1, max = 32)
	@Column(name = "size", nullable = false)
	private String size;

	@Size(min = 1, max = 32)
	@Column(name = "os", nullable = false)
	private String os;
	
	@Column(name = "delete_flag", nullable = false)
	private int delflag;
	
	@Pattern(message = "IP address format is not right", regexp = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))")
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "wifi_flag", nullable = false)
	private int wififlag;
	
	@Column(name = "port", nullable = false)
	private int port;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vendor_id", nullable = true)
	private Vendor vendor;

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Mobile() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
	
	public int getDelflag() {
		return delflag;
	}

	public void setDelflag(int value) {
		this.delflag = value;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public int getWififlag() {
		return wififlag;
	}

	public void setWififlag(int value) {
		this.wififlag = value;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}