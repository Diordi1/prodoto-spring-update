package com.diordel.prodoto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diordel.prodoto.dao.tododao;
import com.diordel.prodoto.service.todo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class todocontroller {
	@Autowired
	tododao td;
	
	@GetMapping(path="/testtodo")
	public String test() {
		return "testing testtodo";
	}
	@GetMapping(path="/listtodo")
	public ResponseEntity<?> lis1(@RequestParam String username){
		List<todo> t1=td.getAll(username);
		if(username.equals("")) {
			return new ResponseEntity<>("No user found",HttpStatus.BAD_REQUEST);
			
		}else {
			return new ResponseEntity<>(t1,HttpStatus.OK);
			
		}
	
	}
	@PostMapping(path="/addtodo")
	public ResponseEntity<?> addtodo(@RequestBody  todo t1){
		if(t1.getUsername().equals("")) {
			return new ResponseEntity<>("No User found",HttpStatus.BAD_REQUEST);
			
		}else {
			td.addTodo(t1);
			
			return new ResponseEntity<>(td.getAll(t1.getUsername()),HttpStatus.OK);
			
		}
	} 
	@PostMapping(path="/addtodo/update/{id}")
	public ResponseEntity<?> updatetodo(@RequestBody todo t1,@PathVariable Long id){
		if(td.getTodo(id)!=null) {
			td.addTodo(t1);
			return new ResponseEntity<>(td.getAll(t1.getUsername()),HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>("No Todo exist",HttpStatus.BAD_REQUEST);
		}
		
	}
	@DeleteMapping(path="/deletetodo/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable Long id,@RequestParam String username){
		if(td.getTodo(id)!=null) {
			td.deleteTodo(username, id);
			
			return new ResponseEntity<>(td.getAll(username),HttpStatus.OK);
		}else {
			return new ResponseEntity<>("No Todo exist",HttpStatus.BAD_REQUEST);
			
		}
	}
	@GetMapping(path="/findtodo")
	public ResponseEntity<?> findTodo(@RequestParam Long id){
		if(td.getTodo(id)==null) {
			return new ResponseEntity<>("No todo Fund",HttpStatus.BAD_REQUEST);
			
		}else {
			return new ResponseEntity<>(td.getTodo(id),HttpStatus.OK);
					
		}
	}
}
