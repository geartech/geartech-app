package com.geartech.app.services.auth;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.geartech.app.models.entities.UserEntity;
import com.geartech.app.models.repositories.settings.UserRepository;
import com.geartech.app.security.CurrentUser;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.role:ADMIN}")
    private String adminRole;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	// Se for adm em memoria
    	if (username.equals(adminUsername)) {
             UserEntity userAdmin = new UserEntity();
             userAdmin.setPersonalNumber(adminUsername);
             userAdmin.setPassword(adminPassword);
             userAdmin.setExpiration(LocalDateTime.now().plusMinutes(20));
             userAdmin.setActive(true);
             
             List<GrantedAuthority> authorities = null;
             List<String> permissions = new ArrayList<>();
             permissions.add(adminRole);
             authorities = buildUserAuthority(permissions);
             return buildUserForAuthentication(userAdmin, authorities);	
        }
    	
    	// Login via banco
        UserEntity user = userRepository.findByPersonalNumber(username)
        		.orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND WITH NAME: " + username));
           
        List<GrantedAuthority> authorities = null;
        List<String> permissions = new ArrayList<>();
        authorities = buildUserAuthority(permissions);
        
        return buildUserForAuthentication(user, authorities);	
    }
    
    private CurrentUser buildUserForAuthentication(UserEntity user, List<GrantedAuthority> authorities) {
    	
    	boolean enabled = user.getActive();
    	boolean accountNonExpired = user.getExpiration().isAfter(LocalDateTime.now());
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		boolean resetPassword = user.getResetPassword() == null ? false : user.getResetPassword();
		
		return new CurrentUser(
				user.getPersonalNumber(), 
				user.getPassword(), 
				enabled, 
				accountNonExpired, 
				credentialsNonExpired, 
				accountNonLocked, 
				authorities,
				user.getId(), 
				1L, 
				1L, 
				user.getPersonalNumber(), 
				resetPassword
		);
	}
    
    private List<GrantedAuthority> buildUserAuthority(List<String> permissoes) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		
		for (String permissao : permissoes) {
			setAuths.add(new SimpleGrantedAuthority(permissao));
		}
		
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}
     
}
