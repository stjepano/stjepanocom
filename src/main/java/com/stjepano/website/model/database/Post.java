package com.stjepano.website.model.database;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Date;
import java.util.List;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class Post {
    private Long id;
    private String title;
    private String subtitle;
    private String uri;
    private String content;
    private String markdown;
    private PostStatus status;
    private ObjectNode metaData;
    private boolean commentsClosed;
    private Date created;
    private Date updated;
    private User createdUser;
    private User updatedUser;

    private List<Tag> tags;
    private List<Category> categories;
    private List<Comment> comments;
    private List<PostLog> postLogs;
    private List<Attachment> attachments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public ObjectNode getMetaData() {
        return metaData;
    }

    public void setMetaData(ObjectNode metaData) {
        this.metaData = metaData;
    }

    public boolean isCommentsClosed() {
        return commentsClosed;
    }

    public void setCommentsClosed(boolean commentsClosed) {
        this.commentsClosed = commentsClosed;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<PostLog> getPostLogs() {
        return postLogs;
    }

    public void setPostLogs(List<PostLog> postLogs) {
        this.postLogs = postLogs;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
