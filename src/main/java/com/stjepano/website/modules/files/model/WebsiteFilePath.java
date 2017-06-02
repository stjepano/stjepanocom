package com.stjepano.website.modules.files.model;

import org.apache.commons.io.FilenameUtils;

/**
 * Represents website file path.
 *
 * {@link WebsiteFilePath} can not contain relative values, therefore . and .. are forbidden. Also path must start with /.
 *
 * If {@link WebsiteFilePath} represents a path to directory, the path must end with /.
 */
public class WebsiteFilePath {
    private final String path;

    public WebsiteFilePath(String path) {
        path = FilenameUtils.separatorsToUnix(path);
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("WebsiteFilePath '" + path + "' does not start with /");
        }

        String[] parts = path.split("/");
        for (String part : parts) {
            if (part.equals(".")) {
                throw new IllegalArgumentException("WebsiteFilePath '" + path + "' contains . (current dir), which is illegal!");
            }
            if (part.equals("..")) {
                throw new IllegalArgumentException("WebsiteFilePath '" + path + "' contains .. (parent dir), which is illegal!");
            }
        }

        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }

    /** Return true if path represents file */
    public boolean isFile() {
        return !path.endsWith("/");
    }

    /** Return true if path represents directory path. */
    public boolean isDirectory() {
        return path.endsWith("/");
    }

    public String getName() {
        return FilenameUtils.getName(path);
    }

    public String getBaseName() {
        return FilenameUtils.getBaseName(path);
    }

    public boolean isRoot() {
        return this.path.equals("/");
    }

    /** Get extension but without dot */
    public String getExtension() {
        return FilenameUtils.getExtension(path);
    }

    public WebsiteFilePath getParent() {
        if (isRoot()) {
            throw new RuntimeException("Could not determine parent of root directory!");
        }
        String input = path;
        if (isDirectory()) {
            // because FilenameUtils.getPath() treats /a/b/c/ => /a/b/c/ but /a/b/c => /a/b/
            input = input.substring(0, input.length() - 1);
        }
        String parentPath = FilenameUtils.getPath(input);
        return new WebsiteFilePath(parentPath);
    }

    public WebsiteFilePath resolveFile(String filename) {
        if (!this.isDirectory()) {
            throw new RuntimeException("Can resolve only if this is directory!");
        }
        return new WebsiteFilePath(this.toString() + filename);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebsiteFilePath that = (WebsiteFilePath) o;

        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
