package com.example.delta_project;

import com.google.gson.annotations.SerializedName;

public class comments {
    @SerializedName("userid")
    String user;
    @SerializedName("postid")
    String post;
    @SerializedName("content")
    String content;

    public comments(String user, String post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public String getPost() {
        return post;
    }

    public String getContent() {
        return content;
    }
}
