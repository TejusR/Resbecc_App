package com.example.delta_project;

import com.google.gson.annotations.SerializedName;

public class friends {
 @SerializedName("fromuser")
    String from;
 @SerializedName("touser")
    String to;
 @SerializedName("status")
 String status;

    public friends(String from, String to,String status) {
        this.from = from;
        this.to = to;
        this.status=status;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getStatus() {
        return status;
    }
}
