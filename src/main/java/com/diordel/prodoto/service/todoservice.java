package com.diordel.prodoto.service;

import java.util.List;

public interface todoservice {
	public String testTodoService();
	public List<todo> getAll(String user);
	public List<todo> deleteTodo(String user,Long id);
	public List<todo> update(Long id,todo t1);
	public todo getTodo(Long id);
//	List<todo> getAll(String user);
	
	
	
}
