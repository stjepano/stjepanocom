package com.stjepano.website.modules.files.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

/**
 * Represents website file
 */
public class WebsiteFile {

    private final WebsiteFilePath path;
    private final WebsiteFileAttributes websiteFileAttributes;
    private final String mimeType;

    private WebsiteFile(WebsiteFilePath path, WebsiteFileAttributes websiteFileAttributes, String mimeType) {
        this.path = path;
        this.websiteFileAttributes = websiteFileAttributes;
        this.mimeType = mimeType;
    }

    public WebsiteFilePath getPath() {
        return path;
    }

    public WebsiteFileAttributes getWebsiteFileAttributes() {
        return websiteFileAttributes;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static WebsiteFile create(WebsiteFilePath path, Path realPath) {
        if (!Files.exists(realPath)) {
            throw new RuntimeException("File or directory '"+ realPath.toString() +"' does not exist");
        }
        try {
            // todo: get real file attributes
            WebsiteFileAttributes websiteFileAttributes = new WebsiteFileAttributes(true, true, new Date(Files.getLastModifiedTime(realPath).toMillis()));
            WebsiteFile websiteFile = new WebsiteFile(path, websiteFileAttributes, Files.probeContentType(realPath));
            return websiteFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
