package com.example.delta_project;

import com.google.gson.annotations.SerializedName;

public class messages {
    @SerializedName("messagefrom")
    String from;
    @SerializedName("messageto")
    String to;
    @SerializedName("content")
    String content;

    public messages(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getContent() {
        return content;
    }
}
