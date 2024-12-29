package com.diordel.prodoto.rep;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.diordel.prodoto.service.todo;
@Component
public interface todorep extends JpaRepository<todo, Long>{
	public List<todo> findAllByUsername(String name);
	
}
