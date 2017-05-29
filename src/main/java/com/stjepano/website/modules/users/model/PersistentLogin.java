package com.stjepano.website.modules.users.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Used for persistent logins ...
 */
@Entity
public class PersistentLogin {
    @Column(nullable = false)
    private String username;
    @Id
    private String series;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }
}
