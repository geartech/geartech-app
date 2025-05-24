package com.geartech.app.security;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Valid
@Getter
@Setter
public class AuthRequest {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
}

