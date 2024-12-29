package com.diordel.prodoto.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diordel.prodoto.jwtvalid;
import com.diordel.prodoto.onboardusers;
import com.diordel.prodoto.dao.onboardrep;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController

public class authcontroller {
	@Autowired
	onboardrep or;
	
	@GetMapping(path="/temp1")
	public String st1() {
		return "test";
	}
	@PostMapping(path="/signup")
	public ResponseEntity<?> signup(@RequestBody onboardusers obu){
//		SecretKey sk=Keys.hmacShaKeyFor(jwtvalid.key.getBytes());
		if(or.findByEmail(obu.getEmail()).size()==0) {
			or.save(obu);
			return new ResponseEntity<>("Logged in",HttpStatus.CREATED);
			
		}else {
			return new ResponseEntity<>("User already exists",HttpStatus.BAD_REQUEST);
			
		}
	}
	@PostMapping(path="/signin")
	public ResponseEntity<?> signin(@RequestBody onboardusers obu){
		if(or.findByEmail(obu.getEmail()).size()>0) {
			if(or.findByEmail(obu.getEmail()).get(0).getPassword().equals(obu.getPassword())) {
				SecretKey sk=Keys.hmacShaKeyFor(jwtvalid.key.getBytes());
				String autho[]=new String[] {"admin","user"};
				
				String jwt=Jwts.builder().signWith(sk).claim("email", obu.getEmail()).setIssuer("ADMIN").claim("authorities", autho).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+1000*60*60)).compact();
				List<GrantedAuthority> l1=new ArrayList<>();
				
				for(int i=0;i<autho.length;i++) {
					l1.add(new SimpleGrantedAuthority("ROLE_"+autho[i]));
					
				}
				Authentication auth=new UsernamePasswordAuthenticationToken(obu.getEmail(), null,l1);
				SecurityContextHolder.getContext().setAuthentication(auth);
				return new ResponseEntity<>(jwt,HttpStatus.OK);
				
			}else {
				return new ResponseEntity<>("Wrong Credentials",HttpStatus.BAD_REQUEST);
				
			}
		}else {
			return new ResponseEntity<>("Please Signup",HttpStatus.BAD_REQUEST);
			
		}
	}
}
