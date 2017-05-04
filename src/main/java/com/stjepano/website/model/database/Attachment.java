package com.stjepano.website.model.database;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class Attachment {
    private AttachmentType type;
    private FileRecord file;
    private String name;

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public FileRecord getFile() {
        return file;
    }

    public void setFile(FileRecord file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
