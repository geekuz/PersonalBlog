package org.learning.personalblog.entity;


import org.springframework.security.core.GrantedAuthority;

/// **
// * Defines the roles available in the application.
// * Implements GrantedAuthority to be used directly by Spring Security.
// */
public enum Role implements GrantedAuthority {
    READER,
    CREATOR;

    /**
     * Returns the authority string that Spring Security uses for role-based authorization.
     * For example, for the ADMIN role, this will return "ROLE_ADMIN".
     *
     * @return The string representation of the authority.
     */
    @Override
    public String getAuthority() {
        // The "ROLE_" prefix is a convention for Spring Security.
        return "ROLE_" + this.name();
    }
}