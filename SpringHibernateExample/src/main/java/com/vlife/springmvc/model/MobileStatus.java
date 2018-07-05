package com.vlife.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mobile_status")
public class MobileStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(name = "mobile_id", nullable = false)
	private int mid;

	@NotNull
	@Column(name = "server_id", nullable = false)
	private int sid;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "last_update", nullable = false)
	private String uptime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int id) {
		this.mid = id;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int id) {
		this.sid = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUptime() {
		return uptime;
	}

	public void setLatest(String latest_time) {
		this.uptime = latest_time;
	}

}
