package com.geartech.app.models.repositories.settings;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geartech.app.models.entities.UserProfilesEntity;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfilesEntity, Long> {

    public List<UserProfilesEntity> findByUserId(Long userId);

}
