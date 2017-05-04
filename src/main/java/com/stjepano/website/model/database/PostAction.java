package com.stjepano.website.model.database;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class PostAction {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PostAction{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
