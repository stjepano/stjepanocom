package com.stjepano.website.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Contains admin interface config loaded from application.properties.
 */
@Component
public class AdminConfig {

    @Value("${com.stjepano.website.AdminConfig.title}")
    private String title;
    @Value("${com.stjepano.website.AdminConfig.copyrightMessage}")
    private String copyrightMessage;
    @Value("${com.stjepano.website.AdminConfig.logoText}")
    private String logoText;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCopyrightMessage() {
        return copyrightMessage;
    }

    public void setCopyrightMessage(String copyrightMessage) {
        this.copyrightMessage = copyrightMessage;
    }

    public String getLogoText() {
        return logoText;
    }

    public void setLogoText(String logoText) {
        this.logoText = logoText;
    }
}
