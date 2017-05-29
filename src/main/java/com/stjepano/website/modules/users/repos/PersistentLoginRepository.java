package com.stjepano.website.modules.users.repos;

import com.stjepano.website.modules.users.model.PersistentLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for {@link PersistentLogin}
 */
public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, String> {

    List<PersistentLogin> findByUsername(String username);

}
