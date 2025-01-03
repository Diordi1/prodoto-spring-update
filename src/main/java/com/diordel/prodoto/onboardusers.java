package com.diordel.prodoto;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class onboardusers {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@NotNull
	String email;
	@NotNull
	String password;
	String otp;
	boolean verified;
	
	List<String> authorities;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	
	public onboardusers(Long id, @NotNull String email, @NotNull String password, String otp, boolean verified,
			List<String> authorities) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.otp = otp;
		this.verified = verified;
		this.authorities = authorities;
	}

	public onboardusers() {
		
	}
	
	
}
