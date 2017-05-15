package com.stjepano.website.view;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Form submission for create user and update user.
 */
public class UserDto {
    private Long id;
    private String email;
    private String displayName;
    private String password;
    private String passwordConfirm;
    private String description;
    private boolean blocked;
    private boolean stayOnThisPage;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isStayOnThisPage() {
        return stayOnThisPage;
    }

    public void setStayOnThisPage(boolean stayOnThisPage) {
        this.stayOnThisPage = stayOnThisPage;
    }
}
