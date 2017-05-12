package com.stjepano.website.components;

import com.stjepano.website.model.WebUser;
import com.stjepano.website.services.WebUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * This service is used to initialize database if database is empty.
 */
@Component
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private WebUserService webUserService;

    @PostConstruct
    public void init() {
        if (!webUserService.hasUsers()) {
            WebUser webUser = new WebUser();
            webUser.setEmail("admin@admin");
            webUser.setDisplayName("Administrator");
            webUser.setDescription("Default admin user!");
            webUserService.createUser(webUser, "password");
            log.info("Created default admin user 'admin@admin' with password 'password'");
        }
    }
}
