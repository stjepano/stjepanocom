package com.stjepano.website.model.database;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Date;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class PostLog {
    private Long id;
    private User user;
    private PostAction action;
    private Date created;
    private ObjectNode data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PostAction getAction() {
        return action;
    }

    public void setAction(PostAction action) {
        this.action = action;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public ObjectNode getData() {
        return data;
    }

    public void setData(ObjectNode data) {
        this.data = data;
    }
}
