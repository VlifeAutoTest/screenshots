package com.vlife.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "auth_permission")
public class Permission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	@Column(name = "name", nullable = false)
	private String permission;

	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "available", nullable = false)
	private int available;
	
//	@ManyToMany(cascade=CascadeType.ALL)
//	@JoinTable(name="auth_permission_resources",joinColumns={@JoinColumn(name="permission_id")},inverseJoinColumns={@JoinColumn(name="resource_id")})
//	private Set<Resources> relresources=new HashSet<Resources>(0);
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
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
	
//	 public Set<Resources> getResources() {
//		  return relresources;
//	 }
//		 
//	public void setResources(Set<Resources> resource) {
//		  this.relresources = resource;
//	}
}
