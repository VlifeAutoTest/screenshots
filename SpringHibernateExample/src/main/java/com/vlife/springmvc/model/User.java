package com.vlife.springmvc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "auth_user",uniqueConstraints = {@UniqueConstraint(columnNames="name")})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "passwd", nullable = false)
	@Size(min = 5, max = 8)
	private String passwd;
	@NotNull
	@Email
	private String email;
	
	private Date joined_date;
	private Date lasted_update;
	
	@Column(name = "is_active", nullable = false)
	private int is_active;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = true)
	private Role role;
	
	@Column(name = "delete_flag", nullable = false)
	private int delflag;
	
	public User() {
		
		
	}
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoined_date() {
		return joined_date;
	}

	public void setJoined_date(Date joined_date) {
		this.joined_date = joined_date;
	}

	public Date getLasted_update() {
		return lasted_update;
	}

	public void setLasted_update(Date lasted_update) {
		this.lasted_update = lasted_update;
	}

	public int getIs_active() {
		return is_active;
	}

	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
	
	public int getDelflag() {
		return delflag;
	}

	public void setDelflag(int value) {
		this.delflag = value;
	}
}
