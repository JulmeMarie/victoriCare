package com.victoricare.api.security.roles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole(T(com.victoricare.api.enums.ERight).SUPERADMINISTRATOR, T(com.victoricare.api.enums.ERight).ADMINISTRATOR, T(com.victoricare.api.enums.ERight).MODERATOR, T(com.victoricare.api.enums.ERight).USER)")
public @interface RequireUserRole {

}
