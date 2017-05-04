package com.stjepano.website.model.database;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class CommentStatus {
    private String name;
    private boolean _public;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return _public;
    }

    public void setPublic(boolean _public) {
        this._public = _public;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
