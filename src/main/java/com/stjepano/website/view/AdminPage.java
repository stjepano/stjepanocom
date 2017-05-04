package com.stjepano.website.view;

/**
 * Contains information about certain page.
 */
public class AdminPage {
    private final String path;
    private final String title;
    private final String description;
    private final String faIcon;
    private final Link[] hierarchy;

    public AdminPage(String path, String title, String description, String faIcon, Link[] hierarchy) {
        this.path = path;
        this.title = title;
        this.description = description;
        this.faIcon = faIcon;
        this.hierarchy = hierarchy;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFaIcon() {
        return faIcon;
    }

    public Link[] getHierarchy() {
        return hierarchy;
    }

}
