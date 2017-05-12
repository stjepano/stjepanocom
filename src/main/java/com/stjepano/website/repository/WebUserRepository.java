package com.stjepano.website.repository;

import com.stjepano.website.model.WebUser;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User repository ...
 */
public interface WebUserRepository extends PagingAndSortingRepository<WebUser, Long> {

}