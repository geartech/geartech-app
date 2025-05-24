package com.geartech.app.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CurrentUser extends User {
	
	private static final long serialVersionUID = 1L;
	
	private final Long idUser; 
	private Long idCompany;
	private final Long idCenter;
	private final String personalNumber;
	private final String userName;
	private final Boolean resetPassword;
	
	
	public CurrentUser(String username, String password) {
		super(username, password, null);
		this.idUser = null;
		this.idCompany = null;
		this.idCenter = null;
		this.personalNumber = null;
		this.userName = null;
		this.resetPassword = null;	
	}

	public CurrentUser(String username, String password, boolean enabled, boolean accountNonExpired, 
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			Long idUser, Long idCompany, Long idCenter, String personalNumber, Boolean resetPassword) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	    
		this.idUser = null;
		this.idCompany = null;
		this.idCenter = null;
		this.personalNumber = null;
		this.userName = null;
		this.resetPassword = null;	
	}

	public Long getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}

	public Long getIdUser() {
		return idUser;
	}

	public Long getIdCenter() {
		return idCenter;
	}

	public String getPersonalNumber() {
		return personalNumber;
	}

	public String getUserName() {
		return userName;
	}

	public Boolean getResetPassword() {
		return resetPassword;
	}

}