package com.example.delta_project;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class callpost {
    @SerializedName("message")
    String message;
    @SerializedName("posts")
    List<posts> post;

    public callpost(String message, List<posts> post) {
        this.message = message;
        this.post = post;
    }

    public String getMessage() {
        return message;
    }

    public List<posts> getPost() {
        return post;
    }
}
