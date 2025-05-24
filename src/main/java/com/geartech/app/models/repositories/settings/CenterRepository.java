package com.geartech.app.models.repositories.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geartech.app.models.entities.CenterEntity;

@Repository
public interface CenterRepository extends JpaRepository<CenterEntity, Long> {
    // Additional query methods can be defined here
}
