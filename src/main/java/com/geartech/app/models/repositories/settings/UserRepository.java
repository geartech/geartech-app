package com.geartech.app.models.repositories.settings;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geartech.app.models.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
    Optional<UserEntity> findByPersonalNumber(String personalNumber);
    
}
