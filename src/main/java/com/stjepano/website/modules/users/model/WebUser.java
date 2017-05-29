package com.stjepano.website.modules.users.model;

import javax.persistence.*;
import java.util.Date;

/**
 * The user domain class.
 */
@Entity
public class WebUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private boolean blocked;
    private String imageUri;
    @Column(unique = true)
    private String displayName;
    private String description;
    private String hashedPassword;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
    @ManyToOne
    private WebUser createdWebUser;
    @ManyToOne
    private WebUser updatedWebUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public WebUser getCreatedWebUser() {
        return createdWebUser;
    }

    public void setCreatedWebUser(WebUser createdWebUser) {
        this.createdWebUser = createdWebUser;
    }

    public WebUser getUpdatedWebUser() {
        return updatedWebUser;
    }

    public void setUpdatedWebUser(WebUser updatedWebUser) {
        this.updatedWebUser = updatedWebUser;
    }
}
