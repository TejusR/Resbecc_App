package com.example.delta_project;

import com.google.gson.annotations.SerializedName;

public class authres {
    @SerializedName("message")
    String message;
    @SerializedName("token")
    String token;

    public authres(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
