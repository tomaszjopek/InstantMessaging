package com.example.instantmessaging.Model;

import java.util.Date;

/**
 * Created by Tomek on 2017-02-27.
 */

public class Message {
    private String message;
    private String user;
    private long time;

    public Message(String message, String user) {
        this.message = message;
        this.user = user;
        this.time = new Date().getTime();
    }

    public Message(String message, String user, long time) {
        this.message = message;
        this.user = user;
        this.time = time;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
