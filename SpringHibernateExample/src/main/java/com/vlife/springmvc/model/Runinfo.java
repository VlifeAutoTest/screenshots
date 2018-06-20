package com.vlife.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="runinfo")
public class Runinfo {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	
	private int vid;
	private int mid;
	private String resource;
	private String app;
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}
	
	
	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}
	

}