package com.stjepano.website.modules.files.model;

import java.util.Date;

/**
 * Attributes for {@link WebsiteFile}.
 *
 * List of attributes:
 * <ul>
 *     <li>readable</li>
 *     <li>writeable</li>
 *     <li>lastModifiedDate</li>
 * </ul>
 */
public class WebsiteFileAttributes {

    private final boolean readable;
    private final boolean writeable;
    private final Date lastModifiedDate;

    public WebsiteFileAttributes(boolean readable, boolean writeable, Date lastModifiedDate) {
        this.readable = readable;
        this.writeable = writeable;
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isReadable() {
        return readable;
    }

    public boolean isWriteable() {
        return writeable;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
}
