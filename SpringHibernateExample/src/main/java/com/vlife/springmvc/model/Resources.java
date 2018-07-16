package com.vlife.springmvc.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "auth_resources")
public class Resources {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	@Size(min = 1, max = 128)
	@Column(name = "resource", nullable = false)
	private String resource;

	@NotEmpty
	@Column(name = "method", nullable = false)
	private String method;


	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "available", nullable = false)
	private int available;
	
	@ManyToMany(mappedBy="relresources")
	private Set<Role> relrole=new HashSet<Role>(0);

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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
	
	 public Set<Role> getRelrole() {
		  return relrole;
	 }
		 
	 public void setRelrole(Set<Role> relrole) {
		 this.relrole = relrole;
	 }
}
