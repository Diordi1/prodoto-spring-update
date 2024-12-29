package com.diordel.prodoto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class jwtFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jwtTok=request.getHeader(jwtvalid.header);
		if(jwtTok!=null) {
			String toke=jwtTok.substring(7);
			SecretKey sk=Keys.hmacShaKeyFor(jwtvalid.key.getBytes());
			Claims cc=Jwts.parserBuilder().setSigningKey(sk).build().parseClaimsJws(toke).getBody();
			String email=String.valueOf(cc.get("email"));
			String authorities=String.valueOf(cc.get("authorities"));
			String temp=authorities.replace('[', ' ');
			String temp1=temp.replace(']', ' ');
			String st1[]=temp1.split(",");
			List<GrantedAuthority> l1=new ArrayList<>();
			
			for(int i=0;i<st1.length;i++) {
				l1.add(new SimpleGrantedAuthority("ROLE_"+st1[i]));
				
			}
			Authentication auth=new UsernamePasswordAuthenticationToken(email, null,l1);
			SecurityContextHolder.getContext().setAuthentication(auth);
			
			
			
		}else {
			System.out.println("not possi");
			
		}
		doFilter(request, response, filterChain);
//		doFilterInternal(request, response, filterChain);
	}
	
}
