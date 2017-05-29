package com.stjepano.website.components;

import com.stjepano.website.modules.users.model.WebUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Set of development utils for manual testing ...
 */
@Component
public class DevUtils {

    public Page<WebUser> createRandomUsers(int count, int pageNum, int totalPages) {
        final Random random = new Random();
        List<WebUser> webUserList = IntStream.rangeClosed(1, count).mapToObj(id -> {
            WebUser webUser = new WebUser();
            webUser.setId((long)id);
            webUser.setEmail(RandomStringUtils.randomAlphabetic(8) + "@" + RandomStringUtils.randomAlphabetic(5) + "." + RandomStringUtils.randomAlphabetic(3));
            webUser.setBlocked(random.nextBoolean());
            webUser.setDescription(RandomStringUtils.randomAlphanumeric(100));
            webUser.setDisplayName(RandomStringUtils.randomAlphabetic(16));
            webUser.setCreated(new Date());
            webUser.setUpdated(new Date());
            return webUser;
        }).collect(Collectors.toList());
        return new PageImpl<WebUser>(webUserList,new PageRequest(pageNum-1, count),totalPages*count);
    }

}
