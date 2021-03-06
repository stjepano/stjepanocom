package com.stjepano.website.modules.users.services.security;

import com.stjepano.website.modules.users.model.WebUser;
import com.stjepano.website.modules.users.services.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        return new WebsiteUserDetails(webUser);
    }
}
