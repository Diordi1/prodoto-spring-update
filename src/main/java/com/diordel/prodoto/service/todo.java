package com.diordel.prodoto.service;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
@Entity
public class todo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@NotNull
	String username;
	
	@NotNull
	String heading;
	@NotNull
	String description;
	
	LocalDate date;

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getHeading() {
		return heading;
	}


	public void setHeading(String heading) {
		this.heading = heading;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public todo(Long id, @NotNull String username, @NotNull String heading, @NotNull String description,
			LocalDate date) {
		super();
		this.id = id;
		this.username = username;
		this.heading = heading;
		this.description = description;
		this.date = date;
	}


	public todo() {
		
	}
	
	
}
