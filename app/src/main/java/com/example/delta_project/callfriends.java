package com.example.delta_project;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class callfriends {
    @SerializedName("cf")
    List<friends> cf;
    @SerializedName("frreq")
    List<friends> frreq;
    @SerializedName("allusers")
    List<users> allusers;
    @SerializedName("user")
    String user;

    public callfriends(List<friends> cf, List<friends> frreq, List<users> allusers, String user) {
        this.cf = cf;
        this.frreq = frreq;
        this.allusers = allusers;
        this.user = user;
    }

    public List<friends> getCf() {
        return cf;
    }

    public List<friends> getFrreq() {
        return frreq;
    }

    public List<users> getAllusers() {
        return allusers;
    }

    public String getUser() {
        return user;
    }
}
