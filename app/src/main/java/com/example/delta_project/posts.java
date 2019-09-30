package com.example.delta_project;

import com.google.gson.annotations.SerializedName;

public class posts {
    @SerializedName("id")
    String id;
    @SerializedName("userid")
    String user;
    @SerializedName("post")
    String content;
    @SerializedName("postdate")
    String date;

    public posts(String id, String user, String content, String date) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
