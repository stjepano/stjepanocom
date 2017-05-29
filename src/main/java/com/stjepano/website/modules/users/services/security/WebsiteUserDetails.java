package com.stjepano.website.modules.users.services.security;

import com.stjepano.website.modules.users.model.WebUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class WebsiteUserDetails implements UserDetails {

    public static final String ADMIN_ROLE = "admin";

    private final WebUser webUser;
    private final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();


    public WebsiteUserDetails(WebUser webUser) {
        this.webUser = webUser;
        grantedAuthorities.add(new SimpleGrantedAuthority(ADMIN_ROLE));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.webUser.getHashedPassword();
    }

    @Override
    public String getUsername() {
        return this.webUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.webUser.isBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public WebUser getWebUser() {
        return webUser;
    }
}
