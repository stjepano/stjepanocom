package com.stjepano.website.view;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class Message {

    private static final String SUCCESS = "success";
    private static final String WARNING = "warning";
    private static final String INFO = "info";
    private static final String DANGER = "danger";

    private final String type;
    private final String text;

    private Message(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public static Message info(String text) {
        return new Message(INFO, text);
    }

    public static Message success(String text) {
        return new Message(SUCCESS, text);
    }

    public static Message warning(String text) {
        return new Message(WARNING, text);
    }

    public static Message danger(String text) {
        return new Message(DANGER, text);
    }
}
