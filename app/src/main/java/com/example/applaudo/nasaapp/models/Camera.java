package com.example.applaudo.nasaapp.models;

import com.google.gson.annotations.SerializedName;

public class Camera {

    @SerializedName("name")
    private String mName;

    public Camera(String mName) {

        this.mName = mName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }


}
