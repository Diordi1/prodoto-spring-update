package com.diordel.prodoto.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diordel.prodoto.jwtvalid;
import com.diordel.prodoto.onboardusers;
import com.diordel.prodoto.dao.onboardrep;
import com.diordel.prodoto.service.mailService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.MessagingException;

@RestController

public class authcontroller {
	@Autowired
	onboardrep or;
	@Autowired
	mailService ms;
	
	@GetMapping(path="/temp1")
	public String st1() {
		return "test";
	}
	@PostMapping(path="/signup")
	public ResponseEntity<?> signup(@RequestBody onboardusers obu) throws MessagingException{
//		SecretKey sk=Keys.hmacShaKeyFor(jwtvalid.key.getBytes());
		if(or.findByEmail(obu.getEmail()).size()==0) {
			String temp=randomOtp();
			ms.checkSend(obu.getEmail(), "verify your otp", "Your Verification to the otp is "+temp);
			
			obu.setOtp(temp);
			obu.setAuthorities(Arrays.asList("ROLE_USER"));
			
			or.save(obu);
			return new ResponseEntity<>("Logged in",HttpStatus.CREATED);
			
		}else {
			return new ResponseEntity<>("User already exists",HttpStatus.BAD_REQUEST);
			
		}
	}
	@PostMapping(path="/verify")
	public ResponseEntity<?> verification(@RequestParam String email,@RequestParam String otp){
		if(or.findByEmail(email)!=null) {
			if(otp.equals(or.findByEmail(email).get(0).getOtp()))
			{
				onboardusers u1=or.findByEmail(email).get(0);
				u1.setVerified(true);
				or.save(u1);
				
				return new ResponseEntity<>("verified",HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>("Not verified",HttpStatus.BAD_REQUEST);
				
			}
		}else {
			return new ResponseEntity<>("No user Found",HttpStatus.FORBIDDEN);
			
		}
	}
	@PostMapping(path="/signin")
	public ResponseEntity<?> signin(@RequestBody onboardusers obu) {
		if(or.findByEmail(obu.getEmail()).size()>0) {
			if(or.findByEmail(obu.getEmail()).get(0).getPassword().equals(obu.getPassword())) {
				
				if(or.findByEmail(obu.getEmail()).get(0).isVerified()) {
					
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
					try {
						
						ms.checkSend(obu.getEmail(), "verify your otp", "Your Verification to the otp is "+or.findByEmail(obu.getEmail()).get(0).getOtp());
						return new ResponseEntity<>("Mail sent",HttpStatus.CREATED);
						
					}catch(Exception e){
						System.out.println(e);
					}
					
				}
				
			}else {
				return new ResponseEntity<>("Wrong Credentials",HttpStatus.BAD_REQUEST);
				
			}
		}else {
			return new ResponseEntity<>("Please Signup",HttpStatus.BAD_REQUEST);
			
		}
		return null;
	}
	static String randomOtp() {
		Random rand=new Random();
		int otp=1000+rand.nextInt(9000);
		return otp+"";
		
	}
}
