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
	private int sid;
	private String resource;
	private String app;
	private String s_time;
	private String e_time;
	private String image_path;
	private String log_file;
	private String zip_file;
	private String status;
	
	
	
	
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
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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
	
	public String getStime() {
		return s_time;
	}

	public void setStime(String stime) {
		this.s_time = stime;
	}
	
	public String getEtime() {
		return e_time;
	}

	public void setEtime(String etime) {
		this.e_time = etime;
	}
	
	public String getImagepath() {
		return image_path;
	}

	public void setImagepath(String img) {
		this.image_path = img;
	}
	
	public String getZip() {
		return zip_file;
	}

	public void setZip(String zfile) {
		this.zip_file = zfile;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getLogFile() {
		return log_file;
	}

	public void setLogFile(String lfile) {
		this.log_file = lfile;
	}
}