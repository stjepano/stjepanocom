package com.stjepano.website.model.database;

import java.util.List;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class MimeType {
    private Long id;
    private String name;
    private String iconUri;
    private String description;
    private List<FileExtension> fileExtensions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FileExtension> getFileExtensions() {
        return fileExtensions;
    }

    public void setFileExtensions(List<FileExtension> fileExtensions) {
        this.fileExtensions = fileExtensions;
    }
}
