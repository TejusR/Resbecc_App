package com.example.delta_project;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class callprofile {
    @SerializedName("user")
    String user;
    @SerializedName("userposts")
    List<posts> userposts;
    @SerializedName("friends")
    List<friends> userfrnds;

    public callprofile(String user, List<posts> userposts, List<friends> userfrnds) {
        this.user = user;
        this.userposts = userposts;
        this.userfrnds = userfrnds;
    }

    public String getUser() {
        return user;
    }

    public List<posts> getUserposts() {
        return userposts;
    }

    public List<friends> getUserfrnds() {
        return userfrnds;
    }
}
