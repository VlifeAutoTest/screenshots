package com.vlife.springmvc.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vendor")
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;
	
//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "vendor", cascade=CascadeType.REMOVE)
//	private Set<Application> appInfo = new HashSet<Application>(0);

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
	
//	public Set<Application> getAppInfo() {
//		return this.appInfo;
//	}
//
//	public void setAppInfo(Set<Application> appinfo) {
//		this.appInfo = appinfo;
//	}

}
