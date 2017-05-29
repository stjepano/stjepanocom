package com.stjepano.website.modules.users.repos;

import com.stjepano.website.modules.users.model.WebUser;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User repository ...
 */
public interface WebUserRepository extends PagingAndSortingRepository<WebUser, Long> {

    WebUser findByEmail(String email);

}
