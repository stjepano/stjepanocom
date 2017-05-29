package com.stjepano.website.modules.files.model;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Information about a file ...
 *
 * Note that file paths are relative paths in upload folder, not the full path with upload folder.
 * The path also starts with /
 *
 * Directory path should end with /
 *
 * Mime type will be null for non regular files!
 */
public class WebsiteFileInfo {
    private final String path;
    private final boolean dir;
    private final boolean regularFile;
    private final Date lastModifiedTime;
    private final String mimeType;

    private WebsiteFileInfo(String path, boolean dir, boolean regularFile, Date lastModifiedTime, String mimeType) {
        this.mimeType = mimeType;
        if (lastModifiedTime == null) throw new NullPointerException();
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("path must start with /");
        }
        if (dir && !path.endsWith("/")) {
            throw new IllegalArgumentException("directory paths must end with /");
        }
        if (!dir && path.endsWith("/")) {
            throw new IllegalArgumentException("non-directory paths must not end with /");
        }
        this.path = path;
        this.dir = dir;
        this.regularFile = regularFile;
        this.lastModifiedTime = lastModifiedTime;
    }

    /** Get relative path (path is relative to upload folder) */
    public String getPath() {
        return path;
    }

    /** Return true if directory */
    public boolean isDir() {
        return dir;
    }

    /** Return true if file is regular file */
    public boolean isRegularFile() {
        return regularFile;
    }

    /** Gets date when file was updated */
    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    /** Gets file mime type */
    public String getMimeType() {
        return mimeType;
    }

    /** Return filename. If file is directory then return dir name */
    public String getFilename() {
        return FilenameUtils.getName(this.path);
    }

    /** Gets extension */
    public String getExtension() {
        String filename = getFilename();
        return FilenameUtils.getExtension(filename);
    }

    /** Gets path of a file */
    public String getParent() {
        return FilenameUtils.getPath(this.path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebsiteFileInfo that = (WebsiteFileInfo) o;

        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    public static WebsiteFileInfo createFromRealPath(String uploadFolder, Path realPath) {
        if (!Files.exists(realPath)) {
            throw new IllegalArgumentException("File '" + realPath.toString() + "' does not exist!");
        }
        boolean dir = Files.isDirectory(realPath);
        boolean regularFile = Files.isRegularFile(realPath);
        String mimeType = null;
        if (Files.isRegularFile(realPath)) {
            try {
                mimeType = Files.probeContentType(realPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not determine mime type of file '" + realPath.toString() + "'", e);
            }
        }
        Date lastModifiedTime = null;
        try {
            lastModifiedTime = new Date(Files.getLastModifiedTime(realPath).toMillis());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Path uploadPath = Paths.get(uploadFolder).normalize().toAbsolutePath();
        realPath = realPath.normalize().toAbsolutePath();
        if (!realPath.startsWith(uploadPath)) {
            throw new RuntimeException("Trying to create FileRecord from a file that is not in uploadFolder. File path: " + realPath.toAbsolutePath().toString() + ", upload path: " + uploadFolder);
        }
        Path relativeFilePath = uploadPath.relativize(realPath).normalize();
        // now relativeFilePath should contain only interesting part to us
        String resultName = "/" + relativeFilePath.toString();
        if (dir && !resultName.endsWith("/")) {
            resultName = resultName + "/";
        }
        return new WebsiteFileInfo(resultName, dir, regularFile, lastModifiedTime, mimeType);
    }
}
