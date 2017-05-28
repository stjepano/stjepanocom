package com.stjepano.website.repository;

import com.stjepano.website.model.PersistentLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for {@link PersistentLogin}
 */
public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, String> {

    List<PersistentLogin> findByUsername(String username);

}
