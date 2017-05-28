package com.stjepano.website.services;

import com.stjepano.website.components.AdminConfig;
import com.stjepano.website.model.WebUser;
import com.stjepano.website.repository.WebUserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * This is {@link com.stjepano.website.model.WebUser} service
 */
@Service
public class WebUserService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private WebUserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean hasUsers() {
        return repository.count() > 0;
    }

    public Page<WebUser> getPage(int pageNumber) {
        return repository.findAll(new PageRequest(pageNumber, adminConfig.getUsersPerPage(), new Sort(Sort.Direction.ASC, "email")));
    }

    public WebUser createUser(WebUser prototype, String password) {
        prototype.setCreated(new Date());
        prototype.setUpdated(new Date());
        prototype.setHashedPassword(bCryptPasswordEncoder.encode(password));
        return repository.save(prototype);
    }

    public Optional<WebUser> findById(Long id) {
        if (id == null) throw new NullPointerException("id");
        return Optional.ofNullable(repository.findOne(id));
    }

    public Optional<WebUser> findByEmail(String email) {
        return Optional.ofNullable(repository.findByEmail(email));
    }

    public void deleteUsers(List<Long> ids) {
        ids.forEach(repository::delete);
    }

    public WebUser updateUser(WebUser webUser, String password) {
        if (password != null && !password.isEmpty()) {
            // update password
            webUser.setHashedPassword(bCryptPasswordEncoder.encode(password));
        }
        webUser.setUpdated(new Date());
        return repository.save(webUser);
    }
}
