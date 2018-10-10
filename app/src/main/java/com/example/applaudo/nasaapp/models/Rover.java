package com.example.applaudo.nasaapp.models;

import com.google.gson.annotations.SerializedName;

public class Rover {

    @SerializedName("landing_date")
    private String mLandingDate;
    @SerializedName("launch_date")
    private String mLaunchDate;

    public Rover(String mLandingDate, String mLaunchDate) {
        this.mLandingDate = mLandingDate;
        this.mLaunchDate = mLaunchDate;
    }

    public String getLandingDate() {
        return mLandingDate;
    }

    public void setLandingDate(String mLandingDate) {
        this.mLandingDate = mLandingDate;
    }

    public String getLaunchDate() {
        return mLaunchDate;
    }

    public void setLaunchDate(String mLaunchDate) {
        this.mLaunchDate = mLaunchDate;
    }
}
