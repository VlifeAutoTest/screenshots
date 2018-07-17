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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "auth_roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "available", nullable = false)
	private int available;

	@ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "auth_role_resource", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
			@JoinColumn(name = "resource_id") })
	@Cascade(value = { org.hibernate.annotations.CascadeType.SAVE_UPDATE,
			org.hibernate.annotations.CascadeType.DELETE })
	private Set<Resources> relresources = new HashSet<Resources>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "role", cascade=CascadeType.REMOVE)
	private Set<User> userInfo = new HashSet<User>(0);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String role) {
		this.name = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int value) {
		this.available = value;
	}

	public Set<Resources> getRelresources() {
		return relresources;
	}

	public void setRelresources(Set<Resources> resource) {
		this.relresources = resource;
	}
	
	public Set<User> getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(Set<User> userinfo) {
		this.userInfo = userinfo;
	}

}
