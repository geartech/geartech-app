package com.geartech.app.components;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.geartech.app.models.entities.audit.AuditLogEntity;
import com.geartech.app.models.repositories.audit.AuditLogRepository;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

@Component
public class AuditListener {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @PrePersist
    public void beforeCreate(Object entity) {
        saveAuditLog(entity, "CREATE");
    }

    @PreUpdate
    public void beforeUpdate(Object entity) {
        saveAuditLog(entity, "UPDATE");
    }

    @PreRemove
    public void beforeDelete(Object entity) {
        saveAuditLog(entity, "DELETE");
    }

    private void saveAuditLog(Object entity, String action) {
        AuditLogEntity log = new AuditLogEntity();
        log.setEntityName(entity.getClass().getSimpleName());
        log.setEntityId(getEntityId(entity));
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        log.setPersonalNumber(getCurrentUser());
        log.setDetails(convertEntityToJson(entity));
        log.setMethod(getCallerMethod());

        auditLogRepository.save(log);
    }

    private Long getEntityId(Object entity) {
        try {
            return (Long) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            return null;
        }
    }

    private String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    private String convertEntityToJson(Object entity) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(entity);
        } catch (Exception e) {
            return "Erro ao converter JSON";
        }
    }

    private String getCallerMethod() {
        Optional<String> callerMethod =
                StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
                        .walk(stackStream -> stackStream.skip(3) // Ignora chamadas internas do
                                                                 // listener
                                .findFirst()
                                .map(frame -> frame.getClassName() + "." + frame.getMethodName()));

        return callerMethod.orElse("Unknown");
    }
}

