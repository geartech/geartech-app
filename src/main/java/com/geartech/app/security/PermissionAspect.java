package com.geartech.app.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

    @Before("@annotation(permission)")
    public void checkPermission(JoinPoint joinPoint, Permission permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasPermission = false;
        for (String role : permission.value()) {
            String requiredRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
            hasPermission = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(requiredRole));
            if (hasPermission) break;
        }

        if (!hasPermission) {
            throw new AccessDeniedException(permission.message());
        }
    }
}
