package com.stjepano.website.security;

import com.stjepano.website.model.WebUser;
import com.stjepano.website.services.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This is implementation of spring boot {@link UserDetailsService}
 */
@Service
public class WebsiteUserDetailsService implements UserDetailsService {

    private static final String ADMIN_ROLE = "admin";

    @Autowired
    private WebUserService webUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WebUser webUser = webUserService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(ADMIN_ROLE));
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return grantedAuthorities;
            }

            @Override
            public String getPassword() {
                return webUser.getHashedPassword();
            }

            @Override
            public String getUsername() {
                return webUser.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return !webUser.isBlocked();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
