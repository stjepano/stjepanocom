package com.stjepano.website.view;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class Link {
    private final String url;
    private final String text;
    private final String faIcon;
    private final boolean active;

    public Link(String url, String text, String faIcon) {
        this(url, text, faIcon, false);
    }

    public Link(String url, String text, String faIcon, boolean active) {
        this.url = url;
        this.text = text;
        this.faIcon = faIcon;
        this.active = active;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }

    public String getFaIcon() {
        return faIcon;
    }

    public boolean isActive() {
        return active;
    }
}
