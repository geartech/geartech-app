package com.geartech.app.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geartech.app.services.auth.RefreshTokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
        	
        	Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        	
        	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userDetailsService.loadUserByUsername(userDetails.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);

            String refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());

            Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setSecure(true); // só em HTTPS
            refreshCookie.setPath("/auth/refresh"); // ou "/"
            refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 dias
            response.addCookie(refreshCookie);
            
            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        String username = refreshTokenService.validateRefreshToken(refreshToken);
        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String jwt = jwtUtil.generateToken(userDetails);
            // Você pode gerar novo refresh token, se quiser, e invalidar o antigo
            return ResponseEntity.ok(new AuthResponse(jwt));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> request, HttpServletResponse response) {
        String refreshToken = request.get("refreshToken");
        refreshTokenService.removeRefreshToken(refreshToken);
        
        // zerar o cookie no browser
        Cookie cookie = new Cookie("refreshToken", "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/auth/refresh");
        response.addCookie(cookie);
        
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getLoggedUser(Authentication authentication) {
        String username = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // Monte a resposta conforme sua entidade, ex:
        return ResponseEntity.ok(Map.of(
            "username", userDetails.getUsername(),
            "authorities", userDetails.getAuthorities()
        ));
    }

    
}

