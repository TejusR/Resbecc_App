package com.example.delta_project;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class callmessage {
    @SerializedName("messages")
    List<messages> mes;

    public callmessage(List<messages> mes) {
        this.mes = mes;
    }

    public List<messages> getMes() {
        return mes;
    }
}
