package com.example.applaudo.nasaapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Rover implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mLandingDate);
        dest.writeString(this.mLaunchDate);
    }

    protected Rover(Parcel in) {
        this.mLandingDate = in.readString();
        this.mLaunchDate = in.readString();
    }

    public static final Parcelable.Creator<Rover> CREATOR = new Parcelable.Creator<Rover>() {
        @Override
        public Rover createFromParcel(Parcel source) {
            return new Rover(source);
        }

        @Override
        public Rover[] newArray(int size) {
            return new Rover[size];
        }
    };
}
