package com.stjepano.website.security;

import com.stjepano.website.model.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Used to provide current logged in users details.
 */
@Service
public class WebsiteSecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    public WebUser findLoggedInUser() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails != null) {
            WebsiteUserDetails websiteUserDetails = (WebsiteUserDetails) userDetails;
            return websiteUserDetails.getWebUser();
        }
        return null;
    }
}