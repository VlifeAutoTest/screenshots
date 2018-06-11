package com.vlife.springmvc.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import org.hibernate.validator.constraints.NotEmpty;

import com.vlife.springmvc.model.TestServer;

@Entity
@Table(name="mobile")
public class Mobile {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Size(min=1, max=50)
	@Column(name = "name", nullable = false)
	private String name;
	
	@Size(min=1, max=50)
	@Column(name = "vendor", nullable = false)
	private String vendor;
	

	@Size(min=1, max=10)
	@Column(name = "status", nullable = false)
	private String status;


	@ManyToOne(fetch=FetchType.LAZY,optional=true,cascade=CascadeType.ALL)
	@JoinColumn(name="server_id",nullable = false)
	private TestServer tserver;
	public TestServer getTestServer() {
		return this.tserver;
	}
	
	public void setTestServer(TestServer tserver) {
		this.tserver = tserver;
	}
	
	@NotEmpty
	@Column(name = "uid", unique=true, nullable = false)
	private String uid;
	
	public Mobile(String name, String vendor) {
		this.name = name;
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

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
}