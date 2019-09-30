package com.example.delta_project;

import com.google.gson.annotations.SerializedName;

public class users {
    @SerializedName("username")
    String username;

    public users(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
