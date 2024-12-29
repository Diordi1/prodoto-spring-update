package com.diordel.prodoto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProdotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdotoApplication.class, args);
	}
	@Bean
	public SecurityFilterChain sec(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(http1->http1.requestMatchers("/api/**").permitAll().requestMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().permitAll()			
				);
		http.httpBasic();
		http.formLogin();
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.addFilterBefore(new jwtFilter(), BasicAuthenticationFilter.class);
		return http.build();
		
	}
	@Bean
	public WebMvcConfigurer webc() { 
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").allowedOrigins("http://localhost:5173","http://localhost:5173/").allowCredentials(true).allowPrivateNetwork(true);
				
			}
		};
	}

}
