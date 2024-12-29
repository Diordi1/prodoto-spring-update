package com.diordel.prodoto.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diordel.prodoto.rep.todorep;
import com.diordel.prodoto.service.todo;
import com.diordel.prodoto.service.todoservice;

@Component
public class tododao implements todoservice{

	@Autowired
	todorep tr;
	
	@Override
	public String testTodoService() {
		// TODO Auto-generated method stub
		return "testing";
	}

	@Override
	public List<todo> getAll(String user) {
		// TODO Auto-generated method stub
		
		return tr.findAllByUsername(user);
	}

	@Override
	public List<todo> deleteTodo(String user,Long id) {
		// TODO Auto-generated method stub
	
		tr.delete(tr.findById(id).get());
		return tr.findAllByUsername(user);
	}

	
	public List<todo> update(Long id,todo t1) {
		// TODO Auto-generated method stub
		tr.save(t1);
	
		return tr.findAll();
	}

	@Override
	public todo getTodo(Long id) {
		// TODO Auto-generated method stub
		return tr.findById(id).get();
	}
	public void addTodo(todo t1) {
		tr.save(t1);
		
		
	}


	

	
}
