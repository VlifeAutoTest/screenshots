package com.vlife.springmvc.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "runinfo")
public class Runinfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int vid;
	private String mid;
	private int sid;
	private String resource;
	private String app;
	private Date stime;
	private Date etime;
	private String image_path;
	private String log_file;
	private String zip_file;
	private String status;
	private String style;

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

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
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

	public Date getStime() {
		return stime;
	}

	public void setStime(String stime) throws ParseException {
		Date sdate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		sdate = format.parse(stime);
		this.stime = sdate;
	}

	public Date getEtime() {
		return etime;
	}

	public void setEtime(String etime) throws ParseException {
		Date edate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		edate = format.parse(etime);
		this.etime = edate;
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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}