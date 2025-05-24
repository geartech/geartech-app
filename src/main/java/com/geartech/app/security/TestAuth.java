package com.geartech.app.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestAuth {

	public static void main(String[] args) {
		  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        String senha = "snake";
	        String hash = encoder.encode(senha);
	        System.out.println(hash);

	}
}
