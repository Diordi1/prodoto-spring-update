package com.diordel.prodoto.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.diordel.prodoto.onboardusers;
@Component
public interface onboardrep extends JpaRepository<onboardusers, Long>{
	public List<onboardusers> findByEmail(String email);
	
}
