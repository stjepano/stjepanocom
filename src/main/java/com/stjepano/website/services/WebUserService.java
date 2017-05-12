package com.stjepano.website.services;

import com.stjepano.website.components.AdminConfig;
import com.stjepano.website.model.WebUser;
import com.stjepano.website.repository.WebUserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * This is {@link com.stjepano.website.model.WebUser} service
 */
@Service
public class WebUserService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private WebUserRepository repository;

    public boolean hasUsers() {
        return repository.count() > 0;
    }

    public Page<WebUser> getPage(int pageNumber) {
        return repository.findAll(new PageRequest(pageNumber, adminConfig.getUsersPerPage()));
    }

    public WebUser createUser(WebUser prototype, String password) {
        prototype.setCreated(new Date());
        prototype.setUpdated(new Date());
        prototype.setBlocked(false);
        prototype.setSalt(genSalt());
        prototype.setHashedPassword(hashPassword(password, prototype.getSalt()));
        return repository.save(prototype);
    }

    private String genSalt() {
        return DigestUtils.md5Hex(RandomStringUtils.randomAlphanumeric(16));
    }

    private String hashPassword(String password, String salt) {
        return DigestUtils.sha512Hex(salt + password);
    }
}
