package com.Abdo_Fahmi.Recipe_Bank.security.model;


import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
public class UserPrincipal implements UserDetails {
    @Getter
    private final String id;
    private final String username;
    private final String email;
    private final String password;
    private final Collection<GrantedAuthority> authorities;

    // Used lombok getter to handle implemented methods
    ////////// PLACEHOLDERS /////////////
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
