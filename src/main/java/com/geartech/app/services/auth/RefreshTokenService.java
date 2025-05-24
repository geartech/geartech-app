package com.geartech.app.services.auth;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.geartech.app.models.entities.auth.RefreshTokenEntity;
import com.geartech.app.models.repositories.auth.RefreshTokenRepository;

@Service
public class RefreshTokenService {
	
	@Value("${jwt.refresh-token-expiration}")
	private Long refreshTokenExpirationMs;
	
	
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public String createRefreshToken(String username) {
        String token = UUID.randomUUID().toString();
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setToken(token);
        refreshToken.setUsername(username);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpirationMs));
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public String validateRefreshToken(String token) {
        Optional<RefreshTokenEntity> refreshTokenOpt = refreshTokenRepository.findByToken(token);
        if (refreshTokenOpt.isPresent()) {
            RefreshTokenEntity refreshToken = refreshTokenOpt.get();
            if (refreshToken.getExpiryDate().isAfter(Instant.now())) {
                return refreshToken.getUsername();
            } else {
                // Token expirado, remova do banco
                refreshTokenRepository.delete(refreshToken);
            }
        }
        return null;
    }

    public void removeRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
