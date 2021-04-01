package com.example.worktalk.Models;

public class ChatMesajModel {

    private String userKey,otherKey,textType, time,messageText,from,fotoUrl;
    Boolean seen;
    public ChatMesajModel() {

    }

    public ChatMesajModel(String userKey, String otherKey, String textType, String time, String messageText, String fotoUrl, Boolean seen, String from) {
        this.userKey = userKey;
        this.otherKey = otherKey;
        this.textType = textType;
        this.time = time;
        this.messageText = messageText;
        this.seen = seen;
        this.from=from;
        this.fotoUrl=fotoUrl;
    }



    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getOtherKey() {
        return otherKey;
    }

    public void setOtherKey(String otherKey) {
        this.otherKey = otherKey;
    }

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "ChatMesajModel{" +
                "userKey='" + userKey + '\'' +
                ", otherKey='" + otherKey + '\'' +
                ", textType='" + textType + '\'' +
                ", time='" + time + '\'' +
                ", messageText='" + messageText + '\'' +
                ", seen=" + seen +
                '}';
    }
}

