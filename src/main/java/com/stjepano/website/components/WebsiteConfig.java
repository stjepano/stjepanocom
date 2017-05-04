package com.stjepano.website.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Contains site info that is used on every blog page.
 */
@Component
public class WebsiteConfig {
    @Value("${com.stjepano.website.WebsiteConfig.name}")
    private String name;
    @Value("${com.stjepano.website.WebsiteConfig.heading}")
    private String heading;
    @Value("${com.stjepano.website.WebsiteConfig.subHeading}")
    private String subHeading;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }
}
