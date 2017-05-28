package com.stjepano.website.security;

import com.stjepano.website.model.WebUser;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Implementation of {@link PasswordEncoder}
 */
public class WebsitePasswordEncoder implements PasswordEncoder {

    private final WebUser webUser;

    public WebsitePasswordEncoder(WebUser webUser) {
        this.webUser = webUser;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }
}
