package com.geartech.app.models.repositories.settings;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geartech.app.models.entities.ProfilePermissionEntity;

@Repository
public interface ProfilePermissionRepository extends JpaRepository<ProfilePermissionEntity, Long> {

    public List<ProfilePermissionEntity> findByProfileId(Long profileId);

}
