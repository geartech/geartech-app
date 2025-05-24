package com.geartech.app.models.repositories.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geartech.app.models.entities.audit.AuditLogEntity;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {

}
